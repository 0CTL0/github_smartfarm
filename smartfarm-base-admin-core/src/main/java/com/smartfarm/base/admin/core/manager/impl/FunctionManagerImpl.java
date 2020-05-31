package com.smartfarm.base.admin.core.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.FunctionDao;
import com.smartfarm.base.admin.core.entity.Function;
import com.smartfarm.base.admin.core.manager.FunctionManager;

@Service
public class FunctionManagerImpl implements FunctionManager {

    @Resource
    private FunctionDao functionDao;

    public int insert(Function function) {
        return functionDao.insert(function);
    }

    public int updateById(Function function) {
        return functionDao.updateById(function);
    }

    public List<Function> queryFuncListByAdminId(Long id, Long parentId) {
        return functionDao.queryFuncListByAdminId(id, parentId);
    }

    public Function queryFuncById(Long id) {
        return functionDao.queryFuncById(id);
    }

    public List<Function> queryFuncListByParentId(Long id) {
        return functionDao.queryFuncListByParentId(id);
    }
}
