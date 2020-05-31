package com.smartfarm.base.admin.core.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.AdminRoleDao;
import com.smartfarm.base.admin.core.entity.AdminRole;
import com.smartfarm.base.admin.core.manager.AdminRoleManager;

@Service
public class AdminRoleManagerImpl implements AdminRoleManager {

    @Resource
    private AdminRoleDao adminRoleDao;

    public int insert(AdminRole adminRole) {
        return adminRoleDao.insert(adminRole);
    }

    public List<Long> queryRoleByAdminId(Long id) {
        return adminRoleDao.queryRoleByAdminId(id);
    }

    public int deleteAllRoleByAdminId(Long id) {
        return adminRoleDao.deleteAllRoleByAdminId(id);
    }
}
