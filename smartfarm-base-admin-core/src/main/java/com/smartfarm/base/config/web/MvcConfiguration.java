package com.smartfarm.base.config.web;


import com.smartfarm.base.interceptor.FuncInterceptor;
import com.smartfarm.base.interceptor.LoginHandlerInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.ServletException;

/**
 * 开启Mvc,自动注入spring容器。 WebMvcConfigurerAdapter：配置视图解析器
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean
 * 
 * @author xiangze
 *
 */
@Configuration
@EnableWebMvc // 等价于<mvc:annotation-driven/>,该标签会自动覆盖官方自动配置的/static、/public、/META-INF/resources、/resources
// WebMvcConfigurerAdapter已失效
public class MvcConfiguration  implements WebMvcConfigurer,ApplicationContextAware {
	// Spring容器
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//springboot2默认将localhost/test和localhost/test.do认为是2个url————》经验证不对，而且springboot的默认mvc配置会忽视掉后缀，然后按先动态后静态匹配的规则来处理请求
	//先将localhost/test.*和localhost/test映射到统一方法
	//再指定拦截.do
	//这为什么会把静态资源给拦截下来!————》dispatcherServlet会接管所有请求（包括静态资源请求），如果修改默认的UrlMapping为*.do，如果静态资源不加后缀，那么一定会导致静态资源无法加载。
	//因为springboot默认的mvc配置能够统一使用处理静态动态请求，根本不需要用到后缀，只要确保静态动态请求的匹配url不冲突即可。
//	@Configuration
//	public class CustomwebMvcConfigurer implements WebMvcConfigurer {
//		@Override
//		public void configurePathMatch(PathMatchConfigurer configurer) {
//			configurer.setUseRegisteredSuffixPatternMatch(true);
//		}
//	}
//	@Bean
//	public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
//		ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
//		servletServletRegistrationBean.addUrlMappings("*.htm");
//		return servletServletRegistrationBean;
//	}



	/**
	 * 定义默认的静态资源请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}


	/**
	 * 配置url资源访问到指定目录
	 *
	 * 配置释放静态资源
	 * 如此处不限制访问，可直接通过 spring.mvc.static-path-pattern=/** 即可允许所有请求到资源目录
	 *
	 * 区分：/表示根目录下，/**表示根目录下的所有文件
	 *
	 * @param registry 资源处理器注册
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/","classpath:/static/",
				"classpath:/WEB-INF/", "classpath:/","classpath:/templates/");

//		原：
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
//		registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/baidu/work/image/upload/");
//		registry.addResourceHandler("/upload/**").addResourceLocations("file:/D:/projectdev/image/upload/");
	}

	//默认是index.html，修改默认的欢迎页面设置——》相当于设置一个虚拟目录"/"的Controller???——》没用吧，都没经过dispathServlet
	//可是页面显示也设置成功？——》直接越过了dispathServlet？
	// 就是index没有被视图解析，可能是视图解析器没找到页面？——》与dispathServlet冲突？
	//	http://localhost:8080/admin/shop/product.htm，访问其他页面，一样是解析错误，视图解析器有问题
	//解析器前后缀的问题？
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/").setViewName("index");

		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

		WebMvcConfigurer.super.addViewControllers(registry);

	}


	/**
	 * 创建viewResolver
	 * 
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// 设置Spring 容器
		viewResolver.setApplicationContext(this.applicationContext);
		// 取消缓存
		viewResolver.setCache(false);
		// 设置解析的前缀
//		viewResolver.setPrefix("classpath:/WEB-INF/");  //解析不到
//		viewResolver.setPrefix("classpath:/static/");	//复制index.html到static下，可以解析
		viewResolver.setPrefix("classpath:/templates/");	//复制index.html到templates下，可以解析
//		viewResolver.setPrefix("classpath:/templates/WEB-INF/"); //将webapp编译到template下，解析不到
//		viewResolver.setPrefix("classpath:/static/WEB-INF/"); //将webapp下的web-inf和r复制到static下，解析不到
		// 设置试图解析的后缀
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	/**
	 * 文件上传解析器
	 *
	 * 一直解析不到WEB-INF下的页面
	 * nested exception is org.thymeleaf.exceptions.TemplateInputException
	 * springboot默认使用thymeleaf模板引擎，为什么解析不到？？？
	 *
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		// 1024 * 1024 * 20 = 20M
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}


//	@Value("${kaptcha.border}")
//	private String border;
//
//	@Value("${kaptcha.textproducer.font.color}")
//	private String fcolor;
//
//	@Value("${kaptcha.image.width}")
//	private String width;
//
//	@Value("${kaptcha.textproducer.char.string}")
//	private String cString;
//
//	@Value("${kaptcha.image.height}")
//	private String height;
//
//	@Value("${kaptcha.textproducer.font.size}")
//	private String fsize;
//
//	@Value("${kaptcha.noise.color}")
//	private String nColor;
//
//	@Value("${kaptcha.textproducer.char.length}")
//	private String clength;
//
//	@Value("${kaptcha.textproducer.font.names}")
//	private String fnames;
//
//	/**
//	 * 由于web.xml不生效了，需要在这里配置Kaptcha验证码Servlet
//	 */
//	@Bean
//	public ServletRegistrationBean servletRegistrationBean() throws ServletException {
//		ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
//		servlet.addInitParameter("kaptcha.border", border);// 无边框
//		servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor); // 字体颜色
//		servlet.addInitParameter("kaptcha.image.width", width);// 图片宽度
//		servlet.addInitParameter("kaptcha.textproducer.char.string", cString);// 使用哪些字符生成验证码
//		servlet.addInitParameter("kaptcha.image.height", height);// 图片高度
//		servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);// 字体大小
//		servlet.addInitParameter("kaptcha.noise.color", nColor);// 干扰线的颜色
//		servlet.addInitParameter("kaptcha.textproducer.char.length", clength);// 字符个数
//		servlet.addInitParameter("kaptcha.textproducer.font.names", fnames);// 字体
//		return servlet;
//	}

	/**
	 * 添加拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		String loginInterceptPath="/**";
//		InterceptorRegistration loginIR = registry.addInterceptor(new LoginHandlerInterceptor());
//		loginIR.addPathPatterns(loginInterceptPath);
//
//		String funcInterceptPath="/admin/**";
//		InterceptorRegistration funcIR = registry.addInterceptor(new FuncInterceptor());
//		funcIR.addPathPatterns(funcInterceptPath);

//		/** 店家管理系统拦截部分 **/
//		String interceptPath = "/shopadmin/**";
//		// 注册拦截器
//		InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
//		// 配置拦截的路径
//		loginIR.addPathPatterns(interceptPath);
//		/** shopauthmanagement page **/
//		loginIR.excludePathPatterns("/shopadmin/addshopauthmap");
//		/** scan **/
//		loginIR.excludePathPatterns("/shopadmin/adduserproductmap");
//		loginIR.excludePathPatterns("/shopadmin/exchangeaward");
//		// 还可以注册其它的拦截器
//		InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
//		// 配置拦截的路径
//		permissionIR.addPathPatterns(interceptPath);
//		// 配置不拦截的路径
//		/** shoplist page **/
//		permissionIR.excludePathPatterns("/shopadmin/shoplist");
//		permissionIR.excludePathPatterns("/shopadmin/getshoplist");
//		/** shopregister page **/
//		permissionIR.excludePathPatterns("/shopadmin/getshopinitinfo");
//		permissionIR.excludePathPatterns("/shopadmin/registershop");
//		permissionIR.excludePathPatterns("/shopadmin/shopoperation");
//		/** shopmanage page **/
//		permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
//		permissionIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
//		/** shopauthmanagement page **/
//		permissionIR.excludePathPatterns("/shopadmin/addshopauthmap");
//		/** scan **/
//		permissionIR.excludePathPatterns("/shopadmin/adduserproductmap");
//		permissionIR.excludePathPatterns("/shopadmin/exchangeaward");
//		/** 超级管理员系统拦截部分 **/
//		interceptPath = "/superadmin/**";
//		// 注册拦截器
//		InterceptorRegistration superadminloginIR = registry.addInterceptor(new SuperAdminLoginInterceptor());
//		// 配置拦截的路径
//		superadminloginIR.addPathPatterns(interceptPath);
//		superadminloginIR.excludePathPatterns("/superadmin/login");
//		superadminloginIR.excludePathPatterns("/superadmin/logincheck");
//		superadminloginIR.excludePathPatterns("/superadmin/main");
//		superadminloginIR.excludePathPatterns("/superadmin/top");
//		superadminloginIR.excludePathPatterns("/superadmin/clearcache4area");
//		superadminloginIR.excludePathPatterns("/superadmin/clearcache4headline");
//		superadminloginIR.excludePathPatterns("/superadmin/clearcache4shopcategory");

	}

}
