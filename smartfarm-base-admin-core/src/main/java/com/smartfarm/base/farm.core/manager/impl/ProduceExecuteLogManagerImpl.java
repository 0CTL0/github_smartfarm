package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.farm.core.dao.ProduceExecuteLogDao;
import com.smartfarm.base.farm.core.dao.ProducePlanDetailDao;
import com.smartfarm.base.farm.core.entity.ProduceExecuteLog;
import com.smartfarm.base.farm.core.entity.vo.ProduceExecuteLogVo;
import com.smartfarm.base.farm.core.manager.ProduceExecuteLogManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月27日 15:45:45
 * @version 1.0
 */
@Service("produceExecuteLogManager")
public class ProduceExecuteLogManagerImpl implements ProduceExecuteLogManager {

	@Resource
	private ProduceExecuteLogDao produceExecuteLogDao;
	
	@Resource
	private ProducePlanDetailDao producePlanDetailDao;
	
	@Override
	public int addExecuteLog(ProduceExecuteLog executeLog) {
		int publish = produceExecuteLogDao.insert(executeLog);
		int update = producePlanDetailDao.updateLogStatusById(executeLog.getPlanDetailId(), (short) 1);
		return publish*update;
	}

	@Override
	public List<ProduceExecuteLogVo> queryExecuteLogs(Long farmId,String content,String planTime,String executeTime,Short status, Integer start, Integer limit) {
		return produceExecuteLogDao.getExecuteLogsPageList(farmId,content,planTime,executeTime,status, start, limit);
	}

	@Override
	public int queryExecuteLogsTotal(Long farmId,String content,String planTime,String executeTime,Short status) {
		return produceExecuteLogDao.getExecuteLogTotal(farmId,content,planTime,executeTime,status);
	}

	@Override
	public int modifyLogShowStatus(Long id, Short isShow) {
		return produceExecuteLogDao.updateShowStatusById(id, isShow);
	}

	@Override
	public int deleteExecuteLog(Long id, Long detailId) {
		int delete = produceExecuteLogDao.deleteById(id);
		int update = producePlanDetailDao.updateLogStatusById(detailId, (short) 0);
		return delete*update;
	}

	@Override
	public List<ProduceExecuteLog> queryLogsByOperator(Long operator) {
		return produceExecuteLogDao.getExecuteLogsByOperator(operator);
	}

	@Override
	public int WeChatPerformLog(ProduceExecuteLog executeLog) {
		ProduceExecuteLog oldExecuteLog = produceExecuteLogDao.getLogById(executeLog.getId());
		if (oldExecuteLog.getPic()!=null && oldExecuteLog.getPic()!=""){
			executeLog.setPic(oldExecuteLog.getPic()+","+executeLog.getPic());
		}
		return produceExecuteLogDao.completeLog(executeLog);
	}

	@Override
	public List<ProduceExecuteLogVo> queryLogsByTypeAndDetail(Long sourceId, Short taskType) {
		return produceExecuteLogDao.getLogsBySourceIdAndTaskType(sourceId, taskType);
	}

	@Override
	public List<ProduceExecuteLogVo> queryLogsByCodeIdAndTypeWe(Long sourceCodeId, Short taskType) {
		return produceExecuteLogDao.getLogsBySCodeIdAndTypeWe(sourceCodeId, taskType);
	}

}
