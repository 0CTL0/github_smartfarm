package com.smartfarm.base.admin.core.dao;


import java.util.List;

import com.smartfarm.base.admin.core.entity.AdminRole;

public interface AdminRoleDao {
	
	/**
	 * 添加
	 * @param adminRole
	 * @return
	 */
    public int insert(AdminRole adminRole);

    /**
     * 根据id修改
     * @param adminRole
     * @return
     */
    public int updateByPrimaryKeySelective(AdminRole adminRole);

	/**
	 * 根据admin id 查找所属的role
	 * @param id
	 * @return
	 */
	public List<Long> queryRoleByAdminId(Long id);

	/**
	 * 根据admin_id 删除所有记录
	 * @param id
	 * @return
	 */
	public int deleteAllRoleByAdminId(Long id);
}