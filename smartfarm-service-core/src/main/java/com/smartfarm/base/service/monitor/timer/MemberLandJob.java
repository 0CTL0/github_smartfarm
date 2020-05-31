package com.smartfarm.base.service.monitor.timer;

import com.smartfarm.base.farm.core.manager.FarmMemberLandManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("memberLandJob")
public class MemberLandJob {
    protected static Logger log = Logger.getLogger(MemberLandJob.class);
    @Resource
    private FarmMemberLandManager farmMemberLandManager;

    public void memberLandOverdue(){
        try {
            log.info("[memberLandJob:memberLandOverdue]update.");
            farmMemberLandManager.updateOverdueLand();
            log.info("[memberLandJob:memberLandOverdue]update success.");
        } catch (Exception e) {
            log.error("[memberLandJob:memberLandOverdue]false.",e);
        }
    }

}

