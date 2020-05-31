package com.smartfarm.base.admin.core.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.smartfarm.base.admin.core.entity.Admin;
import com.smartfarm.base.admin.core.entity.AdminRole;
import com.smartfarm.base.admin.core.entity.Function;
import com.smartfarm.base.admin.core.manager.AdminManager;
import com.smartfarm.base.admin.core.manager.AdminRoleManager;
import com.smartfarm.base.admin.core.manager.FunctionManager;
import com.smartfarm.base.admin.core.manager.RoleManager;
import com.smartfarm.base.util.MD5Util;
import com.smartfarm.base.util.SessionUtil;

@Controller
@RequestMapping("/adminInfo")
public class AdminInfoController {
    private static final Logger log = Logger.getLogger(AdminInfoController.class);
    @Autowired
    private AdminManager adminManager;
    @Autowired
    private AdminRoleManager adminRoleManager;
    @Autowired
	private RoleManager roleManager;
    @Autowired
    private FunctionManager functionManager;

    /**
     * 登录
     * @param req
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("/login")
	@ResponseBody
	public Object login2(HttpServletRequest req,String account, String password){
		Map<String, Object> map = new HashMap<String, Object>(); 
		try {

			log.info("[AdminController:login]{account:" + account +", password:" + password + "}login.");
			Admin admin = adminManager.loginCheck(account, password);
			if (admin == null || admin.getStatus() != (short)1) {
				map.put("logonFlag", false);
				map.put("success", true);
				map.put("msg", "账号或密码错误");
			}else {
				SessionUtil.addAttribute(req, SessionUtil.ADMIN_ID, admin.getId());
                // 菜单配置
                StringBuilder functionListStr = new StringBuilder();
                List<Function> rootFunc = functionManager.queryFuncListByAdminId(admin.getId(), null);

                for (Function function : rootFunc) {
                    List<Function> subFunc = functionManager.queryFuncListByAdminId(admin.getId(), function.getId());
                    if (subFunc != null && !subFunc.isEmpty()) {
                        for (Function function1 : subFunc) {
                            functionListStr.append(String.format("%s,", function1.getUrl()));
                            if(function1.getFuncs() != null && !function1.getFuncs().isEmpty()) {
                            	functionListStr.append(String.format("%s,", function1.getFuncs()));
                            }
                        }
                    } else {
                        functionListStr.append(String.format("%s,", function.getUrl()));
                        if(function.getFuncs() != null && !function.getFuncs().isEmpty()) {
                        	functionListStr.append(String.format("%s,", function.getFuncs()));
                        }
                    }
                }
                SessionUtil.addAttribute(req, SessionUtil.FUNCTION_LIST, functionListStr.toString());
                SessionUtil.addAttribute(req, SessionUtil.FARM_ID, admin.getFarmId());
                map.put("success", true);
                map.put("logonFlag", true);
            }
		} catch (Exception e) {
			log.error("[AdminController:login]false to login",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * sign out
	 */
	@RequestMapping("/signOut")
	public String signOut(HttpServletRequest req) {
		try {
			log.info("[AdminController:signOut]sign out");
			HttpSession session = req.getSession();
			session.invalidate();
		} catch (Exception e) {
			log.error("[AdminController:signOut]false to sign out",e);
		}

		return "redirect:/";
	}

	/**
	 * update password
	 */
	@RequestMapping("/pswUpdate")
	@ResponseBody
	public Object pswUpdate(HttpServletRequest req, String oldPassword, String newPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:pswUpdate]{oldPassword:"+ oldPassword + ",newPassword:" + newPassword + "}update password.");
			Long id = (Long) SessionUtil.getAttribute(req, SessionUtil.ADMIN_ID);
			Admin adminVo = adminManager.queryAdminById(id);
			Admin admin = adminManager.loginCheck(adminVo.getAccount(), oldPassword);
			if (admin == null) {
				map.put("success", false);
				map.put("msg", "密码错误！");
			} else {
				admin.setPassword(MD5Util.MD5X2(newPassword));
				adminManager.updateById(admin);
				map.put("success", true);
			}
		} catch (Exception e) {
			log.error("[AdminInfoController:pswUpdate]fail to update password.",e);
			map.put("success", false);
		}
		return map;
	}

    /**
     * 查询渠道登录账号信息
     * @param req
     * @return
     */
    @RequestMapping("/loginChannelAdminInfo")
	@ResponseBody
	public Object queryChannelAdminInfo(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>(); 
		try {
			log.info("[AdminController:queryChannelAdminInfo]query channel admin info.");
			Long adminId = (Long) SessionUtil.getAttribute(req, SessionUtil.ADMIN_ID);
			map.put("admin", adminManager.queryAdminById(adminId));
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminController:queryChannelAdminInfo]false to query channel admin info",e);
			map.put("success", false);
		}
		return map;
	}
    
    /**
     * 登出系统
     * @param req
     * @return
     */
    @RequestMapping("/loginOut")
	@ResponseBody
	public Object loginOut(HttpServletRequest req){
		Map<String, Object> map = new HashMap<String, Object>(); 
		try {
			log.info("[AdminController:loginOut]login out.");
			SessionUtil.removeAttribute(req, SessionUtil.ADMIN_ID);
		} catch (Exception e) {
			log.error("[AdminController:loginOut]false to login out",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 插入
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Object insert(@ModelAttribute Admin admin) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:insert]insert admin account.");

			admin.setStatus(Integer.valueOf(1).shortValue());
			adminManager.insert(admin);
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:insert]fail to insert admin account.",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 更新remark
	 * @return
	 */
	@RequestMapping("/update/remark")
	@ResponseBody
	public Object updateRemark(Long id, String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:updateRemark]update remark.");
			Admin admin = new Admin();
			admin.setId(id);
			admin.setRemark(remark);
			adminManager.updateById(admin);
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:updateRemark]fail to update remark.",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 更新status
	 * @return
	 */
	@RequestMapping("/update/status")
	@ResponseBody
	public Object updateStatus(Long id, Short status) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:updateStatus]update status.");
			Admin admin = new Admin();
			admin.setId(id);
			admin.setStatus(status);
			adminManager.updateById(admin);
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:updateStatus]fail to update status.",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 用户角色分配
	 * @return
	 */
	@RequestMapping("/roleSet")
	@ResponseBody
	public Object roleSet(Long id, @RequestParam(value = "roles[]") Long[] roles) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:roleSet]admin role set.");
			adminRoleManager.deleteAllRoleByAdminId(id);
			for (Long role : roles) {
				AdminRole adminRole = new AdminRole();
				adminRole.setAdminId(id);
				adminRole.setRoleId(role);
				adminRoleManager.insert(adminRole);
			}
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:roleSet]fail to admin role set.",e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 获取admin的role及所有role
	 * @return
	 */
	@RequestMapping("/adminRole")
	@ResponseBody
	public Object adminRole(Long adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:adminRole]list all admin role and all role.");
			map.put("allRoles", roleManager.queryAllRoleIdAndName());
			map.put("roles", adminRoleManager.queryRoleByAdminId(adminId));
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:adminRole]fail to list all admin role and all role.",e);
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 分页查询管理员信息
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/queryAdminPage")
	@ResponseBody
	public Object queryAdminPage(Integer pageSize, Integer pageNumber, @ModelAttribute Admin admin) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[AdminInfoController:queryAdminPage]query all adminVo.");
			map.put("admins", adminManager.queryAdminSearchPage((pageNumber - 1) * pageSize, pageSize, admin));
			map.put("total", adminManager.countAdminSearch(admin));
			map.put("success", true);
		} catch (Exception e) {
			log.error("[AdminInfoController:queryAdminPage]fail to query all adminVo.",e);
			map.put("success", false);
		}
		return map;
	}
}
