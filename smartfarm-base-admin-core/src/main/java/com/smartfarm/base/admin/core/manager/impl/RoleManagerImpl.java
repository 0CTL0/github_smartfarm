package com.smartfarm.base.admin.core.manager.impl;


import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.smartfarm.base.admin.core.dao.RoleDao;
import com.smartfarm.base.admin.core.entity.Role;
import com.smartfarm.base.admin.core.manager.RoleManager;

@Service
public class RoleManagerImpl implements RoleManager {

    @Resource
    private RoleDao roleDao;

    public int updateRole(Role role) {
        return roleDao.updateById(role);
    }

    public List<Role> queryRoleList() {
        return roleDao.queryRoleList();
    }

    public int insert(Role role) {
        return roleDao.insert(role);
    }

    public List<Role> queryAllRoleIdAndName() {
        return roleDao.queryAllRoleIdAndName();
    }

    public List<Role> queryAllPage(Integer start, Integer pageSize) {
        return roleDao.queryAllPage(start, pageSize);
    }

    public Integer countAllPage() {
        return roleDao.countAllPage();
    }
}
