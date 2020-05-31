package com.smartfarm.base.admin.core.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.RoleFunctionDao;
import com.smartfarm.base.admin.core.entity.RoleFunction;
import com.smartfarm.base.admin.core.manager.RoleFunctionManager;

@Service
public class RoleFunctionManagerImpl implements RoleFunctionManager {

    @Resource
    private RoleFunctionDao roleFunctionDao;

    public int insert(RoleFunction roleFunction) {
        return roleFunctionDao.insert(roleFunction);
    }

    public int deleteByRoleId(long roleId) {
        return roleFunctionDao.deleteByRoleId(roleId);
    }

    public List<Long> queryByRoleId(Long roleId) {
        return roleFunctionDao.queryByRoleId(roleId);
    }
}
