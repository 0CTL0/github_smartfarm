package com.smartfarm.base.admin.core.dao;


import java.util.List;

import com.smartfarm.base.admin.core.entity.RoleFunction;

public interface RoleFunctionDao {

	/**
	 * 添加
	 * @param roleFunction
	 * @return
	 */
    public int insert(RoleFunction roleFunction);

    /**
     * 根据id修改
     * @param roleFunction
     * @return
     */
    public int updateById(RoleFunction roleFunction);

	/**
	 * 根据roleId删除
	 * @param roleId
	 * @return
	 */
	public int deleteByRoleId(long roleId);

	/**
	 * 根据roleId查询
	 */
	public List<Long> queryByRoleId(Long roleId);

}