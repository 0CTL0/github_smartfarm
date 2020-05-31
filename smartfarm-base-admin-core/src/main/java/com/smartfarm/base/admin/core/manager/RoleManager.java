package com.smartfarm.base.admin.core.manager;


import java.util.List;

import com.smartfarm.base.admin.core.entity.Role;

public interface RoleManager {

    /**
     * 添加Role
     * @param role
     * @return
     */
    public int insert(Role role);

    /**
     * 根据id修改
     * @return
     */
    public int updateRole(Role role);

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
    public List<Role> queryAllPage(Integer start, Integer pageSize);

    /**
     * 计算记录数
     */
    public Integer countAllPage();
}
