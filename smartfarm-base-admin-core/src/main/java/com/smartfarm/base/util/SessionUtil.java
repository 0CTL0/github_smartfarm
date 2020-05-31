package com.smartfarm.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	/** adminId */
	public static final String ADMIN_ID = "admin_id";
	
	public static final String FARM_ID = "farm_id";
	
	public static final String FUNCTION_LIST = "function_list";
	
	public static final String MEMBER_ID = "member_id";

	/** 审核人Id */
	public static final String  OPERATOR_ID = "operator_id";
	/**
	 * 添加session
	 * @param req
	 * @param key
	 * @param value
	 */
	public static void addAttribute(HttpServletRequest req, String key, Object value) {
		HttpSession session = req.getSession();
		session.setAttribute(key, value);
	}
	
	/**
	 * 删除session
	 * @param req
	 * @param key
	 */
	public static void removeAttribute(HttpServletRequest req, String key) {
		HttpSession session = req.getSession();
		session.removeAttribute(key);
	}
	
	/**
	 * 获取session
	 * @param req
	 * @param key
	 * @return
	 */
	public static Object getAttribute(HttpServletRequest req, String key) {
		HttpSession session = req.getSession();
		return session.getAttribute(key);
	}
}
