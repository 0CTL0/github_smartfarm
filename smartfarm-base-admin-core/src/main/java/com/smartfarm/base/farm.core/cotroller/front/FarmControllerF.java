package com.smartfarm.base.farm.core.cotroller.front;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;
import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.FarmRentLandDevice;
import com.smartfarm.base.farm.core.entity.FarmRentLandOrder;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.manager.FarmRentLandDeviceManager;
import com.smartfarm.base.monitor.core.manager.CameraMessageManager;
import com.smartfarm.base.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.admin.core.manager.FarmManager;
import com.smartfarm.base.farm.core.entity.vo.FarmTunnelInfoVo;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;
import com.smartfarm.base.farm.core.manager.FarmMemberLandManager;
import com.smartfarm.base.farm.core.manager.FarmRentLandOrderManager;
import com.smartfarm.base.farm.core.manager.FarmWorkingLogManager;
import com.smartfarm.base.shop.core.entity.AccessToken;
import com.smartfarm.base.shop.core.entity.MemberInfo;
import com.smartfarm.base.shop.core.manager.AccessTokenManager;
import com.smartfarm.base.shop.core.manager.MemberInfoManager;

@Controller
@RequestMapping("/farmMP")
public class FarmControllerF {
    private Logger log = Logger.getLogger(FarmControllerF.class);
    @Resource
    private FarmManager farmManager;
    @Resource
    private FarmRentLandOrderManager farmRentOrderManager;
    @Resource
    private AccessTokenManager accessTokenManager;
    @Resource
    private MemberInfoManager memberInfoManager;
    @Resource
    private FarmMemberLandManager farmMemberLandManager;
    @Resource
    private FarmWorkingLogManager farmWorkingLogManager;
    @Resource
    private FarmRentLandDeviceManager farmRentLandDeviceManager;
    @Resource
    private CameraMessageManager cameraMessageManager;

    @RequestMapping("/getFarm")
    @ResponseBody
    public Object getFarm(HttpServletRequest request,Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarm]{enter}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            map.put("farm",farmManager.getFarmInfo(farmId));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarm]false", e);
        }
        return map;
    }


    /**
     * 获取全部的基地和地块信息
     * @return
     */
    @RequestMapping("/getFarmBaseList")
    @ResponseBody
    public Object getFarmBaseList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            log.info("[LandController:getFarmBaseList]enter.");
            Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
            log.info("[LandController:getFarmBaseList]farmId："+farmId);
            map.put("farmBaseList", farmManager.getFarmBaseVoList(farmId));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[LandController:getFarmBaseList]false.", e);
        }
        return map;
    }


    /**
     * 获取地块的详情
     * @param id
     * @return
     */
    @RequestMapping("/getFarmTunnel")
    @ResponseBody
    public Object getFarmTunnel(Long id,Long farmId){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            log.info("[LandController:getFarmTunnel]id:"+id+" farmId:"+farmId);
            FarmTunnelInfoVo farmTunnelInfoVo=farmManager.getFarmTunnelVo(id);
            String detailPicStr=farmTunnelInfoVo.getDetailPic();
            String detailString[]=detailPicStr.split(";");
            map.put("farmTunnel",farmTunnelInfoVo);
            map.put("farmCropsList", farmManager.getFarmCropsList(farmId));
            map.put("detailPicArr",detailString);

            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[LandController:getFarmTunnel]false.", e);
        }
        return map;
    }


    /**
     * 获取农作物详情
     * @param id
     * @return
     */
    @RequestMapping("getFarmCrops")
    @ResponseBody
    public Object getFarmCrops(Long id){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            log.info("[LandController:getFarmCrops]id:"+id);
            FarmCropsInfo farmCropsInfo=farmManager.getFarmCropsInfo(id);
            String carouselStr=farmCropsInfo.getCarousel();
            String[] carouselString =carouselStr.split(";");
            map.put("farmCrops",farmCropsInfo);
            map.put("carouselArr",carouselString);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[LandController:getFarmCrops]false.", e);
        }
        return map;
    }

    /**
     * 获取租用土地的详情信息
     * @param id
     * @return
     */
    @RequestMapping("getFarmRentLandInfo")
    @ResponseBody
    public Object getFarmRentLandInfo(Long id){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            log.info("[LandController:getFarmRentLandInfo]id:"+id);
            map.put("rentLandInfo",farmManager.getFarmRentLandInfo(id));
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[LandController:getFarmRentLandInfo]false.", e);
        }

        return map;
    }

    /**
     * 创建订单
     * @param farmRentLandOrder
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public Object createOrder(HttpServletRequest request,FarmRentLandOrder farmRentLandOrder){
        Map<String,Object> map=new HashMap<String,Object>();
       try{
           log.info("[LandController:createOrder]farmRentLandOrder:"+farmRentLandOrder);
           Long memberId = (Long) SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
           farmRentLandOrder.setMemberId(memberId);
           farmRentLandOrder.setRentTime(DateUtil.formatMaouthDateTime((int)farmRentLandOrder.getQuantity()*12));

           Boolean addOrderStatus=farmRentOrderManager.createRentOrder(farmRentLandOrder);
           map.put("addOrder",addOrderStatus);
           map.put("orderId", farmRentLandOrder.getId());
           map.put("success",true);
       }catch (Exception e){
           map.put("success", false);
           log.error("[FarmController:createOrder]false to create order .",e);

       }
        return map;
    }


    /**
     * 预支付,生成统一交易单
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping("/payForMP")
    @ResponseBody
    public Object payForMP(HttpServletRequest request,Long orderId){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:payForMP]orderId:"+orderId);
            FarmRentLandOrder farmRentLandOrder=farmRentOrderManager.getRentOrderById(orderId);
            if(farmRentLandOrder.getStatus()==1){
                map.put("success",true);
                map.put("error",true);
                map.put("msg","订单已付款");
                return map;
            }
            if(farmRentLandOrder.getStatus()==2){
                map.put("success",true);
                map.put("error",true);
                map.put("msg","订单已超时");
                return map;
            }
            if(farmRentLandOrder.getStatus()==3){
                map.put("success",true);
                map.put("error",true);
                map.put("msg","订单已取消");
                return map;
            }
            map.put("error",false);

            //调用微信支付接口，生成预支付订单信息返回
            Integer payPrice=(int)(NumberUtil.mul(farmRentLandOrder.getRealAmount(),100));  //支付金额以分为单位
            String remoteAddr = request.getRemoteAddr();
            Long businessId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            AccessToken accessToken = accessTokenManager.queryAccessToken(businessId);
            Long memberId = (Long)SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
            MemberInfo memberInfo = memberInfoManager.queryById(memberId);
            String prepayId = WeixinPayUtil.unifiedOrder(accessToken.getAppid(), "租赁地块", (short)5, remoteAddr,
                    payPrice, farmRentLandOrder.getOrderCode(), memberInfo.getOpenid(), farmRentLandOrder.getPayEndTime(),
                    accessToken.getMchid(),accessToken.getPayKey());
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


            map.put("nonceStr", nonceStr);
            map.put("timestamp", timestamp);
            map.put("payPackage", payPackage);
            map.put("signType", signType);
            map.put("paySign", paySign);

            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:payForMP]false.",e);

        }
        return map;
    }


    /**
     * 获取用户的全部订单
     * @param memberId
     * @return
     */
    @RequestMapping("/getAllRentOrder")
    @ResponseBody
    public Object getAllRentOrder(Long memberId){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:getAllRentOrder]memberId:"+memberId);
            map.put("rentOrderList", farmRentOrderManager.getAllRentOrder(memberId));
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:getAllRentOrder]false.",e);
        }
        return map;
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @RequestMapping("/cancelRentOrder")
    @ResponseBody
    public Object cancelRentOrder(Long id){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:cleanRentOrder]id:"+id);
            farmRentOrderManager.cleanOrder(id);
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:cleanRentOrder]false.",e);
        }
        return map;
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @RequestMapping("/removeRentOrder")
    @ResponseBody
    public Object removeRentOrder(Long id){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:removeRentOrder]id:"+id);
            farmRentOrderManager.removeRentOrder(id);
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:removeRentOrder]false.",e);
        }
        return map;
    }

    /**
     * 获取用户的土地列表
     * @param request
     * @return
     */
    @RequestMapping("/getMemberLandList")
    @ResponseBody
    public Object getMemberLandList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:getMemberLandList]");
            Long memberId = (Long) SessionUtil.getAttribute(request,SessionUtil.MEMBER_ID);
            map.put("memberLandList",farmMemberLandManager.queryLandsByMemberId(memberId));
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:getMemberLandList]false.",e);
        }
        return map;
    }

    /**
     * 获取用户土地的全部劳动日志
     * @param memberLandId
     * @return
     */
    @RequestMapping("/getWorkingLogList")
    @ResponseBody
    public Object getWorkingLogList(Long memberLandId){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:getWorkingLogList]memberLandId:"+memberLandId);
            map.put("landLogVoList",farmMemberLandManager.getWorkingLogVoList(memberLandId));
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:getWorkingLogList]false.",e);
        }
        return map;
    }

    /**
     * 获取员工的委托任务列表
     * @param employeeId
     * @return
     */
    @RequestMapping("/getEmployeeTaskList")
    @ResponseBody
    public Object getEmployeeTaskList(Long employeeId){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[LandController:getEmployeeTaskList]employeeId:"+employeeId);
            List<WorkingLogVo> workingLogVoList=farmWorkingLogManager.getEmployeeTaskList(employeeId);
            for(WorkingLogVo log:workingLogVoList){
                if(log.getPic()!=null){
                    String PicStr=log.getPic();
                    String picArr[]=PicStr.split(";");
                    log.setPicList(Arrays.asList(picArr));
                }
            }
            map.put("employeeTaskList",workingLogVoList);
            map.put("success",true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmController:getEmployeeTaskList]false.",e);
        }
        return map;
    }

    /**
     * 完成委托任务后拍照上传
     * @param id
     * @param file
     * @return
     */
    @RequestMapping("/completeLog")
    @ResponseBody
    public Object completeLog(Long id,@RequestParam(value = "file", required = false) MultipartFile file,String taskType,Short weight, FarmDelegateExpress delegateExpress,Integer index) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[EmployeeInfoController:completeLog] { logId:"+id+"}.");
            log.info("[EmployeeInfoController:completeLog]{ taskType:" + taskType);
            String pic = UploadUtil.uploadFile(file, "/workingLog/",RandomUtil.generateNumber(10));
            WorkingLog workingLog=farmWorkingLogManager.getWorkingLog(id);
            if(workingLog.getPic()==null){
                workingLog.setPic(pic);
            }else{
                workingLog.setPic(workingLog.getPic()+";"+pic);
            }
            workingLog.setStatus((short)2);
            workingLog.setActualExecuteTime(DateUtil.formatCurrentDateTime());
            farmWorkingLogManager.editWorkingLog(workingLog,delegateExpress,taskType,weight,index);//修改，添加判断回填任务类型是否为邮寄

            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[EmployeeInfoController:completeLog] fail to update log data.",e);
        }
        return map;
    }


    /**
     * 获取用户土地的视频直播地址
     * @param memberLandId
     * @return
     */
    @RequestMapping("/getLiveAddress")
    @ResponseBody
    public Object getLiveAddress(Long memberLandId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[EmployeeInfoController:getLiveAddress] { memberLandId:"+memberLandId+"}.");
            FarmMemberLand farmMemberLand=farmMemberLandManager.queryMemberLandById(memberLandId);
            FarmRentLandDevice farmRentLandDevice=farmRentLandDeviceManager.getLandCamera(farmMemberLand.getRentLandId());
            log.info("farmRentLandDevice:"+farmRentLandDevice);
            log.info("liveAddress:"+cameraMessageManager.queryCameraLiveAddressById(farmRentLandDevice.getDeviceId()));
            map.put("liveAddress",cameraMessageManager.queryCameraLiveAddressById(farmRentLandDevice.getDeviceId()));

            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[EmployeeInfoController:getLiveAddress] fail to update log data.",e);
        }
        return map;
    }

}
