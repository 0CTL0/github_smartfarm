package com.smartfarm.base.farm.core.manager;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.vo.ProgressInfoVo;
/**
 * 订单进展接口
 * @author lyq
 *
 */
public interface ProgressInfoManager {
	/**
     * 根据id查看订单进展
     * @param id
     * @return
     */
    public List<ProgressInfo> queryAllOrderProgress(Long id);

    /**
     * 插入项目进度
     * @param progressInfo
     * @param files
     * @return
     * @throws IOException 
     */
	public int insert(ProgressInfo progressInfo, MultipartFile[] files) throws IOException;
	
	public int deleteProgressInfoById(Long id);
	
	/**
     * 根据项目id得到项目进展信息(小程序端)
     * @param id
     * @return
     */
    public List<ProgressInfoVo> getCrowdfundingProgressInfoList(Long id);
}
