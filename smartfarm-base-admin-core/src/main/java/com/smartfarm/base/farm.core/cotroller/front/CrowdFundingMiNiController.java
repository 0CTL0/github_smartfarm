package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.WeixinPayUtil;
import com.smartfarm.base.util.WeixinUtil;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.CrowdfundingOrder;
import com.smartfarm.base.farm.core.entity.vo.CrowFundingOrderVo;
import com.smartfarm.base.farm.core.entity.vo.CrowdFundingVo;
import com.smartfarm.base.farm.core.entity.vo.CrowdfundingOrderDetailVo;
import com.smartfarm.base.farm.core.manager.CrowdFundingManager;
import com.smartfarm.base.farm.core.manager.CrowdFundingOrderManager;
import com.smartfarm.base.farm.core.manager.ProgressInfoManager;
import com.smartfarm.base.farm.core.manager.SettingInfoManager;

/**
 * 众筹小程序端Controller层
 *
 * @author lyq
 */
@Controller()
@RequestMapping("/mini")
public class CrowdFundingMiNiController {

    private Logger log = Logger.getLogger(CrowdFundingMiNiController.class);

    @Resource
    private CrowdFundingManager crowdFundingMapper;

    @Resource
    private CrowdFundingOrderManager crowdFundingOrderManager;

    @Resource
    private ProgressInfoManager progressInfoManager;

    @Resource
    private SettingInfoManager settingInfoManager;

    @Autowired
    private AccessTokenManager accessTokenManager;

    @Autowired
    private MemberInfoManager memberInfoManager;

    /**
     * 根据状态查询全部众筹列表(小程序端)
     *
     * @param status
     * @return
     */
    @RequestMapping("/getAllCrowdfundingListWithStatus")
    @ResponseBody
    public Object getAllCrowdfundingListWithStatus(HttpServletRequest request, short status) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);

            log.info("[CrowdFundingMiNiController:getAllCrowdfundingListWithStatus]query crowdFunding info for page.businessId:" + businessId);

            List<CrowdFundingVo> fundingList = crowdFundingMapper.getAllCrowdfundingListWithStatus(businessId, status);

            if (fundingList.size() == 0) {
                map.put("msg", "众筹列表为空!");
                map.put("success", false);
                return map;
            }
            map.put("crowdfundingList", fundingList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getAllCrowdfundingListWithStatus]false to query crowdFunding list.", e);
        }
        return map;
    }

    /**
     * 得到所有众筹信息
     *
     * @return
     */
    @RequestMapping("/getAllCrowdfundingList")
    @ResponseBody
    public Object getAllCrowdfundingList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            log.info("this is bussinessId :" + businessId);
            log.info("[CrowdFundingMiNiController:getAllCrowdfundingList]query crowdFunding info for page.businessId:" + businessId);
            List<CrowdFundingVo> fundingList = crowdFundingMapper.getAllCrowdfundingList(businessId);
            if (fundingList == null) {
                map.put("msg", "众筹列表为空!");
                map.put("success", false);
                return map;
            }

            map.put("crowdfundingList", fundingList);
            map.put("success", true);

        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getAllCrowdfundingList]false to query crowdFunding list.", e);
        }
        return map;
    }

    /**
     * 根据id查询众筹项目(小程序端)
     *
     * @param id
     * @return
     */
    @RequestMapping("/getCrowdfundingDetail")
    @ResponseBody
    public Object getCrowdfundingDetail(Long id) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        try {
            log.info("[CrowdFundingMiNiController:getCrowdfundingDetail]query crowdFunding info for page. Id =" + id);
            CrowdFundingVo fundingVo = crowdFundingMapper.getCrowdfundingDetail(id);
            if (fundingVo == null) {
                map.put("msg", "该众筹为空!");
                map.put("success", false);
                return map;
            }
            map.put("crowdfunding", fundingVo);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getCrowdfundingDetail]false to query crowdFunding list.", e);
        }
        return map;
    }

    /**
     * 保存订单,返回一个订单编号
     *
     * @param request
     * @param crowdfundingOrder
     * @return
     */
    @RequestMapping("/saveCrowdfundingOrder")
    @ResponseBody
    public Object saveCrowdfundingOrder(HttpServletRequest request, CrowdfundingOrder crowdfundingOrder) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String orderNo = crowdFundingOrderManager.saveCrowdfundingOrder(request, crowdfundingOrder);
            if (orderNo == null) {
                map.put("success", false);
                return map;
            }
            map.put("orderNo", orderNo);
            log.info("[CrowdFundingMiNiController:saveCrowdfundingOrder]save crowdFundingOrder for page.");
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:saveCrowdfundingOrder]false to save crowdFundingOrder.", e);
        }
        return map;
    }

    /**
     * 支付众筹订单
     * @param id
     * @return
     */
    @RequestMapping("/payCrowdfundingOrder")
    @ResponseBody
    public Object payCrowdfundingOrder(HttpServletRequest request,Long id){
        Map<String, Object> map = new HashMap<String, Object>(16);
        try {
        Long businessId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
        CrowdfundingOrder crowdfundingOrder = crowdFundingOrderManager.queryOrderById(id);
        AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
        String remoteAddr = request.getRemoteAddr();
        Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
        MemberInfo memberInfo = memberInfoManager.queryById(memberId);
        //调用微信支付接口
        Integer price = (int) (NumberUtil.mul(crowdfundingOrder.getRealAmount(), 1));
        String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(),
                "众筹商品",
                (short)3,
                remoteAddr,
                price,
                crowdfundingOrder.getOrderNo(),
                memberInfo.getOpenid(),
                "",accessToken.getMchid(),
                accessToken.getPayKey());
            Map<String ,String > signMap=new HashMap<String ,String >();
            signMap.put("appId", accessToken.getAppid());
            String timestamp = WeixinUtil.getTimestamp();
            signMap.put("timeStamp", timestamp);
            String payPackage = "prepay_id=" + prepayId;
            signMap.put("package", payPackage);
            String signType = "MD5";
            signMap.put("signType", signType);
            String nonceStr = WeixinUtil.getRandomStr();
            signMap.put("nonceStr", nonceStr);
            String paySign = WeixinUtil.getPaySign(signMap, accessToken.getPayKey());
            log.info("paySign:{}"+paySign);
            map.put("nonceStr", nonceStr);
            map.put("timestamp", timestamp);
            map.put("payPackage", payPackage);
            map.put("signType", signType);
            map.put("paySign", paySign);
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[OrderController:payForMiniProgram]false to pay for miniProgram.",e);
        }
        return map;
    }




    /**
     * 查询订单列表(小程序端)
     *
     * @param request
     * @param shipStatus
     * @param status
     * @return
     */
    @RequestMapping("/getAllCrowdfundingOrderList")
    @ResponseBody
    public Object getAllCrowdfundingOrderList(HttpServletRequest request, Short shipStatus, Short status) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:getAllCrowdfundingOrderList]query crowdFundingOrder info for page.");
            List<CrowFundingOrderVo> fundingOrderList = crowdFundingOrderManager.getAllCrowdfundingOrderList(status, shipStatus);
            if (fundingOrderList.size() == 0) {
                map.put("msg", "该状态下订单列表为空!");
                map.put("success", false);
                return map;
            }
            map.put("crowdfundingOrderList", fundingOrderList);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getAllCrowdfundingOrderList]false to query crowdFundingOrder list.", e);
        }
        return map;
    }

    /**
     * 根据订单号查询订单详细
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping("/queryOrderDetailByOrderNo")
    @ResponseBody
    public Object getCrowdfundingOrderDetail(HttpServletRequest request, String orderNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:getCrowdfundingOrderDetail]query crowdFundingOrder info for page.");
            CrowdfundingOrderDetailVo orderDetail = crowdFundingOrderManager.queryOrderDetailByOrderNo(orderNo);
            if (orderDetail == null) {
                map.put("msg", "该订单号有误!");
                map.put("success", false);
                return map;
            }
            map.put("crowdfundingOrder", orderDetail);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getCrowdfundingOrderDetail]false to query crowdFundingOrder.", e);
        }
        return map;
    }

    /**
     * 根据项目id获取项目进展
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/getCrowdfundingProgressInfoList")
    @ResponseBody
    public Object getCrowdfundingProgressInfoList(HttpServletRequest request, Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:getCrowdfundingProgressInfoList]query ProgressInfo info for page.");
            map.put("progressInfoList", progressInfoManager.getCrowdfundingProgressInfoList(id));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getCrowdfundingProgressInfoList]false to query ProgressInfo list.", e);
        }
        return map;
    }

    /**
     * 根据项目id得到项目进展信息(小程序端)
     *
     * @param paramCode
     * @return
     */
    @RequestMapping("/getCrowdfundingAgreement")
    @ResponseBody
    public Object getCrowdfundingAgreement(String paramCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:getCrowdfundingAgreement]query SettingInfo.");
            map.put("agreement", settingInfoManager.getCrowdfundingAgreement(paramCode));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getCrowdfundingAgreement]false to query SettingInfo.", e);
        }
        return map;
    }

    /**
     * 根据等级id查询众筹信息
     *
     * @param gradeId
     * @return
     */
    @RequestMapping("/getCrowdFundingDetailByGradeId")
    @ResponseBody
    public Object getCrowdFundingDetailByGradeId(Long gradeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:getCrowdFundingDetailByGradeId]query crowdfunding.");
            map.put("crowdfundingInfo", crowdFundingMapper.getCrowdFundingDetailByGradeId(gradeId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:getCrowdFundingDetailByGradeId]false to query crowdfunding.", e);
        }
        return map;
    }


    /**
     * 查询我的订单列表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/queryMemberOrderList")
    @ResponseBody
    public Object queryMemberOrderList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:queryMemberOrderList]query crowdfundingOrder list.");
            List<CrowdfundingOrderDetailVo> details = crowdFundingOrderManager.queryMemberOrderList(request);
            map.put("crowdOrderList", details);
            log.info("订单列表数量" + details);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:queryMemberOrderList]false to query crowdfundingOrder list.", e);
        }
        return map;
    }

    /**
     * 根据状态查询我的订单列表
     *
     * @param request
     * @param status
     * @return
     */
    @RequestMapping("/queryMemberOrderListWithStatus")
    @ResponseBody
    public Object queryMemberOrderListWithStatus(HttpServletRequest request, short status) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:queryMemberOrderListWithStatus]query crowdfundingOrder list.");
            map.put("crowdOrderList", crowdFundingOrderManager.queryMemberOrderListWithStatus(request, status));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:queryMemberOrderListWithStatus]false to query crowdfundingOrder list.", e);
        }
        return map;
    }

    /**
     * 取消订单
     *
     * @param crowdfundingOrder
     * @return
     */
    @RequestMapping("/cancelCrowdOrder")
    @ResponseBody
    public Object cancelOrder(CrowdfundingOrder crowdfundingOrder) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int count = crowdFundingOrderManager.cancelOrder(crowdfundingOrder);
            if (count < 1) {
                map.put("success", false);
                return map;
            }
            log.info("[CrowdFundingMiNiController:cancelOrder]cancel crowdFundingOrder.");
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:cancelOrder]false to cancel crowdFundingOrder.", e);
        }
        return map;
    }

    /**
     * 根据众筹id查询进展
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryAllOrderProgressById")
    @ResponseBody
    public Object queryAllOrderProgressById(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[CrowdFundingMiNiController:queryOrderList]query queryAllOrderProgressById info for page.");
            map.put("progressList", progressInfoManager.queryAllOrderProgress(id));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[CrowdFundingMiNiController:queryOrderList]false to query queryAllOrderProgressById list.", e);
        }
        return map;
    }
}
