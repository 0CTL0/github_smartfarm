package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月29日 15:59:45
 * @version 1.0
 */
@RequestMapping("/employee")
@Controller
public class EmployeeInfoController {

	private Logger log = Logger.getLogger(EmployeeInfoController.class);
	
	@Resource
	private EmployeeInfoManager employeeInfoManager;
	
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request,String account,String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[EmployeeInfoController:login] begin to verify user is exist.");
			log.info("[EmployeeInfoController:login] { account:"+account+"}.");
			log.info("[EmployeeInfoController:login] { password:"+password+"}.");
			EmployeeInfo employee = employeeInfoManager.verifyEmployee(account, password);
			log.info("[EmployeeInfoController:login] { employee.name:"+employee+"}.");
			if (employee == null) {
				map.put("success", false);
			}else {
				map.put("employee", employee);
				map.put("success", true);
			}
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EmployeeInfoController:login] fail to verify user is exist.",e);
		}
		return map;
	}
}
