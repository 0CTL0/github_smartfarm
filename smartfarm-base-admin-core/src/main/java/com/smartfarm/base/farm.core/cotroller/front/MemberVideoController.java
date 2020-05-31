package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.entity.MemberVideo;
import com.smartfarm.base.farm.core.manager.MemberVideoManager;

@RequestMapping("/video")
@Controller
public class MemberVideoController {

    private Logger log = Logger.getLogger(MemberVideoController.class);

    @Resource
    private MemberVideoManager memberVideoManager;


    /**
     * 上传视频
     * @param request
     * @param video
     * @param files
     * @return
     */
    @RequestMapping("/addVideo")
    @ResponseBody
    public Object addVideo(HttpServletRequest request,Integer index, MemberVideo video, @RequestParam(value = "files",required = false) MultipartFile[] files) {
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:addVideo] begin to add video.");
            if (index==0){
                String coverStr= UploadUtil.uploadFile(files[0], "/video/cover/", RandomUtil.generateNumber(10));
                video.setCover(coverStr);
                log.info("[MemberVideoController:addVideo] { coverStr:" +coverStr+ "}.");
                memberVideoManager.addMemberVideo(video);
            }else {
                String videoStr= UploadUtil.uploadFile(files[0], "/video/", RandomUtil.generateNumber(10));
                log.info("[MemberVideoController:addVideo] { videoStr:" +videoStr+ "}.");
                log.info("video:"+video.getName()+","+video.getMemberId()+","+DateUtil.formatCurrentDate());
                MemberVideo newVideo = memberVideoManager.queryUserLatestVideo(video.getName(),video.getMemberId(),DateUtil.formatCurrentDate());
                log.info(newVideo);
                log.info(newVideo.getId());
                newVideo.setVideoUrl(videoStr);
                memberVideoManager.modifyVideoPathById(newVideo);
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            log.error("[MemberVideoController:addVideo] fail to add video.",e);
        }
        return map;
    }

    /**
     * 查询用户个人的所有视频
     * @param request
     * @param memberId
     * @return
     */
    @RequestMapping("/getMemberVideos")
    @ResponseBody
    public Object getMemberVideos(HttpServletRequest request,@Param("memberId") Long memberId){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:getMemberVideos] begin to query videos by memberId.");
            List<MemberVideo> memberVideos = memberVideoManager.queryMemberVideos(memberId);
            for (MemberVideo video : memberVideos){
                String date = video.getCreateTime();
                video.setCreateTime(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14));
            }
            map.put("memberVideos",memberVideos);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:getMemberVideos] fail to query videos by memberId.",e);
        }
        return  map;
    }

    /**
     * 查询所有视频（已审核通过）
     * @param request
     * @return
     */
    @RequestMapping("/getAllVideos")
    @ResponseBody
    public Object getAllVideos(HttpServletRequest request,Long farmId){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:getAllVideos] begin to query all videos.");
            List<MemberVideo> passedVideos = memberVideoManager.queryAllVideoBePassedAudit(farmId);
            for (MemberVideo video : passedVideos){
                String date = video.getCreateTime();
                video.setCreateTime(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14));
            }
            map.put("passedVideos",passedVideos);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:getAllVideos] fail to query all videos.",e);
        }
        return  map;
    }

    /**
     * 根据id查询单个视频
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/getSingleVideo")
    @ResponseBody
    public Object getSingleVideo(HttpServletRequest request,@Param("id") Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:getSingleVideo] begin to query single video by id.");
            MemberVideo video = memberVideoManager.querySingleVideoById(id);
            String date = video.getCreateTime();
            video.setCreateTime(date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14));
            map.put("video",video);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:getSingleVideo] fail to query single video by id.",e);
        }
        return  map;
    }

    @RequestMapping("/playVideo")
    @ResponseBody
    public Object playVideo(HttpServletRequest request,@Param("id") Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:playVideo] begin to deal with play video.");
            memberVideoManager.addOnePlayTimes(id);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:playVideo] fail to deal with play video.",e);
        }
        return  map;
    }

    /**
     * 提交/发布视频，状态修改为待审核2
     * @param request
     * @param video
     * @return
     */
    @RequestMapping("/publishVideo")
    @ResponseBody
    public Object publishVideo(HttpServletRequest request,MemberVideo video){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:publishVideo] begin to publish video by id.");
            video.setStatus(MemberVideo.STATUS_TO_BE_AUDIT);
            memberVideoManager.modifyVideoStatusById(video);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:publishVideo] fail to publish video by id.",e);
        }
        return  map;
    }

    /**
     * 用户删除视频，根据id设置对应记录status值为0
     * @param request
     * @param video
     * @return
     */
    @RequestMapping("/deleteVideo")
    @ResponseBody
    public Object deleteVideo(HttpServletRequest request,MemberVideo video){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[MemberVideoController:deleteVideo] begin to delete video by id.");
            memberVideoManager.removeVideo(video);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[MemberVideoController:deleteVideo] fail to delete video by id.",e);
        }
        return  map;
    }
}
