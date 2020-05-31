package com.smartfarm.base.farm.core.dao;

import java.util.List;

import com.smartfarm.base.farm.core.entity.vo.MemberVideoVo;
import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.MemberVideo;

public interface MemberVideoDao {

    /**
     * 添加视频记录
     * @param video
     * @return
     */
    public int insert(MemberVideo video);

    /**
     * 查询用户新增的一条视频记录
     * @param name
     * @param memberId
     * @param createDate
     * @return
     */
    public MemberVideo getUserLatestVideo(@Param("name") String name, @Param("memberId") Long memberId, @Param("createDate") String createDate);

    /**
     * 插入视频地址
     * @param video
     * @return
     */
    public int updateVideoUrl(MemberVideo video);

    /**
     * 根据用户id查询其所有视频
     * @param memberId
     * @return
     */
    public List<MemberVideo> getVideosByMemberId(@Param("memberId") Long memberId);

    /**
     * 查询所有已审核通过的视频
     * @return
     */
    public List<MemberVideo> getAllPassedVideo(@Param("farmId") Long farmId);

    /**
     * 根据视频id查询单个视频
     * @param id
     * @return
     */
    public MemberVideo getVideoById(@Param("id") Long id);

    /**
     * 更新视频播放次数（+1）
     * @param video
     * @return
     */
    public int updatePlayTimes(MemberVideo video);

    /**
     * 根据id修改视频的状态status
     * @param video
     * @return
     */
    public int updateStatusById(MemberVideo video);

    /**
     * 根据id删除视频
     * @param video
     * @return
     */
    public int deleteById(MemberVideo video);


    //后台管理员部分
    /**
     * 后台管理员分页查询所有视频
     * @param startDate
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<MemberVideoVo> getVideosPageList(@Param("farmId") Long farmId, @Param("startDate") String startDate, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计符合条件的视频总数
     * @param startDate
     * @param status
     * @return
     */
    public int getVideosTotal(@Param("farmId") Long farmId, @Param("startDate") String startDate, @Param("status") Short status);

    /**
     * 分页查询待审核的视频
     * @param start
     * @param limit
     * @return
     */
    public List<MemberVideoVo> getBeingAuditVideosPageList(@Param("farmId") Long farmId, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
     * 统计待审核的视频数
     * @return
     */
    public int getBeingAuditVideosTotal(@Param("farmId") Long farmId);

    /**
     * 根据id审核视频，修改status
     * @param video
     * @return
     */
    public int auditById(MemberVideo video);




    int insert1(MemberVideo record);
}