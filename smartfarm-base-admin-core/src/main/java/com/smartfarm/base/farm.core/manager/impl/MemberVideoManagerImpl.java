package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import com.smartfarm.base.farm.core.entity.vo.MemberVideoVo;
import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.farm.core.dao.MemberVideoDao;
import com.smartfarm.base.farm.core.entity.MemberVideo;
import com.smartfarm.base.farm.core.manager.MemberVideoManager;

@Service("memberVideoManager")
public class MemberVideoManagerImpl implements MemberVideoManager {

    @Resource
    private MemberVideoDao memberVideoDao;


    @Override
    public int addMemberVideo(MemberVideo video) {
        video.setCreateTime(DateUtil.formatCurrentDateTime());
        video.setStatus(MemberVideo.STATUS_TO_BE_PUBLISHED);
        video.setPlayTimes(0);
        return memberVideoDao.insert(video);
    }

    @Override
    public MemberVideo queryUserLatestVideo(String name, Long memberId, String createDate) {
        return memberVideoDao.getUserLatestVideo(name, memberId, createDate);
    }

    @Override
    public int modifyVideoPathById(MemberVideo video) {
        return memberVideoDao.updateVideoUrl(video);
    }

    @Override
    public List<MemberVideo> queryMemberVideos(Long memberId) {
        return memberVideoDao.getVideosByMemberId(memberId);
    }

    @Override
    public List<MemberVideo> queryAllVideoBePassedAudit(Long farmId) {
        return memberVideoDao.getAllPassedVideo(farmId);
    }

    @Override
    public MemberVideo querySingleVideoById(Long id) {
        return memberVideoDao.getVideoById(id);
    }

    @Override
    public int addOnePlayTimes(Long id) {
        MemberVideo video = memberVideoDao.getVideoById(id);
        video.setPlayTimes(video.getPlayTimes()+1);
        return memberVideoDao.updatePlayTimes(video);
    }

    @Override
    public int modifyVideoStatusById(MemberVideo video) {
        return memberVideoDao.updateStatusById(video);
    }

    @Override
    public int removeVideo(MemberVideo video) {
        video.setStatus(MemberVideo.STATUS_DELETED);
        return memberVideoDao.deleteById(video);
    }


    //后台管理员部分
    @Override
    public List<MemberVideoVo> queryVideosByPage(Long farmId, String startDate, Short status, Integer start, Integer limit) {
        if (startDate != null){
            startDate = startDate.replaceAll("-","");
        }
        return memberVideoDao.getVideosPageList(farmId,startDate,status,start,limit);
    }

    @Override
    public int queryVideosTotal(Long farmId,String startDate, Short status) {
        return memberVideoDao.getVideosTotal(farmId,startDate,status);
    }

    @Override
    public List<MemberVideoVo> queryBeingAuditVideosByPage(Long farmId,Integer start, Integer limit) {
        return memberVideoDao.getBeingAuditVideosPageList(farmId,start, limit);
    }

    @Override
    public int queryBeingAuditVideosTotal(Long farmId) {
        return memberVideoDao.getBeingAuditVideosTotal(farmId);
    }

    @Override
    public int auditVideoById(MemberVideo video) {
        return memberVideoDao.auditById(video);
    }
}
