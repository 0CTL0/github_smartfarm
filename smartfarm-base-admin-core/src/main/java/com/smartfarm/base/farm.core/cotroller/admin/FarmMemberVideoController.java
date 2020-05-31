package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.farm.core.entity.vo.MemberVideoVo;
import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.MemberVideo;
import com.smartfarm.base.farm.core.manager.MemberVideoManager;

@RequestMapping("/farmVideo")
@Controller
public class FarmMemberVideoController {

    private Logger log = Logger.getLogger(FarmMemberVideoController.class);

    @Resource
    private MemberVideoManager memberVideoManager;


    /**
     * 按条件分页查询视频
     * @param request
     * @param startDate
     * @param status
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getVideos")
    @ResponseBody
    public Object getVideos(HttpServletRequest request,String startDate,Short status,Integer pageNumber,Integer pageSize){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmMemberVideoController:getVideos] begin to query all videos.");
            List<MemberVideoVo> videoList = memberVideoManager.queryVideosByPage((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),startDate,status,(pageNumber - 1) * pageSize, pageSize);
            formatDate(videoList);
            map.put("videoList",videoList);
            map.put("total",memberVideoManager.queryVideosTotal((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),startDate,status));
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmMemberVideoController:getVideos] fail to query all videos.",e);
        }
        return  map;
    }


    /**
     * 分页查询待审核的视频
     * @param request
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getVideosBeingAudit")
    @ResponseBody
    public Object getVideosBeingAudit(HttpServletRequest request,Integer pageNumber,Integer pageSize){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmMemberVideoController:getVideosBeingAudit] begin to query being audit videos.");
            List<MemberVideoVo> videoList = memberVideoManager.queryBeingAuditVideosByPage((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID),(pageNumber - 1) * pageSize, pageSize);
            formatDate(videoList);
            map.put("videoList",videoList);
            map.put("total",memberVideoManager.queryBeingAuditVideosTotal((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID)));
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmMemberVideoController:getVideosBeingAudit] fail to query being audit videos.",e);
        }
        return  map;
    }

    /**
     * 审核视频，通过or不通过；修改status，3 or 4
     * @param request
     * @param video
     * @return
     */
    @RequestMapping("/auditVideo")
    @ResponseBody
    public Object auditVideo(HttpServletRequest request,MemberVideo video){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmMemberVideoController:auditVideo] begin to audit video by id.");
            memberVideoManager.auditVideoById(video);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmMemberVideoController:auditVideo] fail to audit video by id.",e);
        }
        return  map;
    }

    /**
     * 删除视频，根据id设置对应记录status值为0
     * @param request
     * @param video
     * @return
     */
    @RequestMapping("/deleteVideo")
    @ResponseBody
    public Object deleteVideo(HttpServletRequest request,MemberVideo video){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmMemberVideoController:deleteVideo] begin to delete video by id.");
            memberVideoManager.removeVideo(video);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmMemberVideoController:deleteVideo] fail to delete video by id.",e);
        }
        return  map;
    }

    private void formatDate(List<MemberVideoVo> videoList) {
        for (MemberVideo video : videoList) {
            String date = video.getCreateTime();
            video.setCreateTime(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10, 12) + ":" + date.substring(12, 14));
        }
    }

}
