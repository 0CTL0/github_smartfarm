package com.smartfarm.base.farm.core.manager.impl;

import com.smartfarm.base.farm.core.dao.FarmSourceTypeDao;
import com.smartfarm.base.farm.core.entity.FarmSourceType;
import com.smartfarm.base.farm.core.manager.FarmSourceTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("farmSourceTypeManager")
public class FarmSourceTypeManagerImpl implements FarmSourceTypeManager {

    @Resource
    private FarmSourceTypeDao sourceTypeDao;


    @Override
    public List<FarmSourceType> querySourceTypeList() {
        return sourceTypeDao.getSourceTypes();
    }
}
