package com.smartfarm.base.admin.core.manager;


import java.util.List;

import com.smartfarm.base.admin.core.entity.AdminRole;

public interface AdminRoleManager {

    /**
     * 插入
     * @param adminRole
     * @return
     */
    public int insert(AdminRole adminRole);

    /**
     * 找到用户所有的权限角色
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
