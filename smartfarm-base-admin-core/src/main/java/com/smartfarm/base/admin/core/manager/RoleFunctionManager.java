package com.smartfarm.base.admin.core.manager;


import java.util.List;

import com.smartfarm.base.admin.core.entity.RoleFunction;

public interface RoleFunctionManager {

    /**
     * 添加
     */
    public int insert(RoleFunction roleFunction);

    /**
     * 根据roleId删除
     */
    public int deleteByRoleId(long roleId);


    /**
     * 根据roleId查找
     */
    public List<Long> queryByRoleId(Long roleId);
}
