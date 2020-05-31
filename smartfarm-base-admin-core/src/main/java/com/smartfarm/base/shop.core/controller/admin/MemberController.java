package com.smartfarm.base.shop.core.controller.admin;

import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/memberAdmin")
public class MemberController {
    private Logger log = Logger.getLogger(MemberController.class);
    @Resource
    private MemberInfoManager memberInfoManager;

    @RequestMapping("/getMemberList")
    @ResponseBody
    public Object getMemberList(HttpServletRequest request, String nickName, Integer pageSize, Integer pageNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        log.info("[MemberController:getMemberList]nickName:"+nickName+" pageSize"+pageSize+" pageNumber"+pageNumber);
        try {
            log.info("[MemberController:getMemberList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query order info for page.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            map.put("memberInfos", memberInfoManager.getPageList(farmId,nickName, (pageNumber - 1) * pageSize, pageSize));
            map.put("total", memberInfoManager.getPageTotal(farmId,nickName));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberController:getMemberList]false to query order list.", e);
        }
        return map;
    }


}
