package com.smartfarm.admin.core.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class BaseIndexController {

//	private Logger log = Logger.getLogger(BaseIndexController.class);
	
	/**
	 * 通用跳转三级
	 * @param url1
	 * @return
	 */
	@RequestMapping("/{url1}/{url2}/{url3}")
	public String toIndexThree(@PathVariable String url1, @PathVariable String url2, @PathVariable String url3, HttpServletRequest request) {
		String url = request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
		return url1 + "/" + url2 + "/" + url3;
//		String reqUrl=url1 + "/" + url2 + "/" + url3;
//		if(reqUrl.contains(".htm")){
//			int i=reqUrl.indexOf(".htm");
//			return reqUrl.substring(0,i);
//		}
//		return reqUrl;
	}
	
	/**
	 * 通用跳转二级
	 * @param url1
	 * @return
	 */
	@RequestMapping("/{url1}/{url2}")
	public String toIndexTwo(@PathVariable String url1,@PathVariable String url2,HttpServletRequest request) {
		String url = request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }
		return url1 + "/" + url2;
//		String reqUrl=url1 + "/" + url2;
//		if(reqUrl.contains(".htm")){
//			int i=reqUrl.indexOf(".htm");
//			return reqUrl.substring(0,i);
//		}
//		return reqUrl;
	}
	
	/**
	 * 通用跳转
	 * @param url
	 * @return
	 */
	@RequestMapping("/{url}")
	public String toIndexOne(@PathVariable String url,HttpServletRequest request) {
		String urlStr = request.getScheme() +"://" + request.getServerName() + ":" +request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
        	urlStr += "?" + request.getQueryString();
        }
		return url;
//		String reqUrl=url;
//		if(reqUrl.contains(".htm")){
//			int i=reqUrl.indexOf(".htm");
//			return reqUrl.substring(0,i);
//		}
//		return reqUrl;
	}

	@RequestMapping("/hello")
	@ResponseBody
	public Object hello(){
		return "hello";
	}

}
