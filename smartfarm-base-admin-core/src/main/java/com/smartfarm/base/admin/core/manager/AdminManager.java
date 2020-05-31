package com.smartfarm.base.admin.core.manager;


import java.util.List;

import com.smartfarm.base.admin.core.entity.Admin;

public interface AdminManager {

	/**
	 * 插入
	 */
	public int insert(Admin admin);

	/**
	 * 根据id修改
	 * @param admin
	 * @return
	 */
	public int updateById(Admin admin);

	/**
	 * 判断用户是否可登录
	 * @param password
	 * @return
	 */
	public Admin loginCheck(String account, String password);
	
	/**
     * 根据id查询
     * @param id
     * @return
     */
    public Admin queryAdminById(Long id);

	/**
	 * page query with search keys
	 * @param start
	 * @param pageSize
	 * @return
	 */
    List<Admin> queryAdminSearchPage(Integer start, Integer pageSize, Admin admin);

	/**
	 * count with search keys
	 * @return
	 */
	Integer countAdminSearch(Admin admin);
}
