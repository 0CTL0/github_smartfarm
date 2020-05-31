package com.smartfarm.base.admin.core.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.smartfarm.base.admin.core.entity.Role;

public interface RoleDao {
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
    public int insert(Role role);

    /**
     * 根据id修改
     * @param role
     * @return
     */
    public int updateById(Role role);

	/**
	 * 列出所有Role
	 * @return
	 */
	public List<Role> queryRoleList();

	/**
	 * 列出所有Role的id和name
	 */
	public List<Role> queryAllRoleIdAndName();

	/**
	 * 分页查询
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<Role> queryAllPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	/**
	 * 计算记录数
	 */
	public Integer countAllPage();
}