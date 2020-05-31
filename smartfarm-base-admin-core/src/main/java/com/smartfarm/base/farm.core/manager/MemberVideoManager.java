package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.farm.core.entity.MemberVideo;
import com.smartfarm.base.farm.core.entity.vo.MemberVideoVo;


public interface MemberVideoManager {

    /**
     * 提交/发布/添加视频
     * @param video
     * @return
     */
    public int addMemberVideo(MemberVideo video);

    /**
     * 查询用户最新一条记录
     * @param name
     * @param memberId
     * @param createDate
     * @return
     */
    public MemberVideo queryUserLatestVideo(String name, Long memberId, String createDate);

    /**
     * 填入视频地址
     * @param video
     * @return
     */
    public int modifyVideoPathById(MemberVideo video);

    /**
     * 查询用户的所有视频
     * @param memberId
     * @return
     */
    public List<MemberVideo> queryMemberVideos(Long memberId);

    /**
     * 查询所有审核通过的视频
     * @return
     */
    public List<MemberVideo> queryAllVideoBePassedAudit(Long farmId);

    /**
     * 根据视频id查询单个视频
     * @param id
     * @return
     */
    public MemberVideo querySingleVideoById(Long id);

    /**
     * 视频播放次数加1
     * @param id
     * @return
     */
    public int addOnePlayTimes(Long id);

    /**
     * 根据id修改视频的状态
     * @param video
     * @return
     */
    public int modifyVideoStatusById(MemberVideo video);

    /**
     * 删除视频，状态status修改为0
     * @param video
     * @return
     */
    public int removeVideo(MemberVideo video);


    //后台管理员部分
    /**
     * 后台管理员分页查询所有视频
     * @param startDate
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<MemberVideoVo> queryVideosByPage(Long farmId, String startDate, Short status, Integer start, Integer limit);

    /**
     * 统计符合条件的视频数
     * @param startDate
     * @param status
     * @return
     */
    public int queryVideosTotal(Long farmId, String startDate, Short status);

    /**
     * 分页查询待审核的视频
     * @param start
     * @param limit
     * @return
     */
    public List<MemberVideoVo> queryBeingAuditVideosByPage(Long farmId, Integer start, Integer limit);

    /**
     * 统计待审核视频数
     * @return
     */
    public int queryBeingAuditVideosTotal(Long farmId);

    /**
     * 审核视频
     * @param video
     * @return
     */
    public int auditVideoById(MemberVideo video);
}
