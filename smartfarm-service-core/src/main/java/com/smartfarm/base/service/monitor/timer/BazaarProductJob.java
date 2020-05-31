package com.smartfarm.base.service.monitor.timer;

import com.smartfarm.base.farm.core.manager.FarmMemberCropsManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("bazaarProductJob")
public class BazaarProductJob {
    protected static Logger log = Logger.getLogger(MemberLandJob.class);
    @Resource
    private FarmMemberCropsManager farmMemberCropsManager;

    public void removeOverdueBazaarProduct(){
        try {
            log.info("[bazaarProductJob:removeOverdueBazaarProduct]update.");
            farmMemberCropsManager.removeOverdueBazaarProduct();
            log.info("[bazaarProductJob:removeOverdueBazaarProduct]update success.");
        } catch (Exception e) {
            log.error("[bazaarProductJob:removeOverdueBazaarProduct]false.",e);
        }
    }

}
