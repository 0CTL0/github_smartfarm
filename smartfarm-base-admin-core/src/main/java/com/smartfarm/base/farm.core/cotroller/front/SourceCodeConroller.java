package com.smartfarm.base.farm.core.cotroller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.vo.ProduceExecuteLogVo;
import com.smartfarm.base.farm.core.entity.vo.SourceCodeVo;
import com.smartfarm.base.farm.core.manager.ProduceExecuteLogManager;
import com.smartfarm.base.farm.core.manager.SourceCodeManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月04日 10:24:51
 * @version 1.0
 */
@RequestMapping("/weSource")
@Controller
public class SourceCodeConroller {

	private Logger log = Logger.getLogger(SourceCodeConroller.class);
	
	@Resource
	private SourceCodeManager sourceCodeManager;
	
	@Resource
	private  ProduceExecuteLogManager executeLogManager;
	
	
	/**
	 * 小程序扫码查询溯源
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("/queryTracing")
	@ResponseBody
	public Object queryTracing(HttpServletRequest request,String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[EmployeeInfoController:queryTracing] begin to query tracing info.");
			log.info("[EmployeeInfoController:queryTracing] { code:"+code+"}.");
			SourceCodeVo sourceCodeVo = sourceCodeManager.querySourceCodeWe(code);
			sourceCodeVo.setFirstSearchTime(DateUtil.formatDateTime(sourceCodeVo.getFirstSearchTime()));
			map.put("sourceCodeVo", sourceCodeVo);
			map.put("success", true);	
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EmployeeInfoController:queryTracing] fail to query tracing info.",e);
		}
		return map;
	}
	
	/**
	 * 小程序按类别查询溯源信息
	 * @param request
	 * @param sourceCodeId
	 * @param type
	 * @return
	 */
	@RequestMapping("/queryTracingInfo")
	@ResponseBody
	public Object queryTracingInfo(HttpServletRequest request,Long sourceCodeId,Short type) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.info("[EmployeeInfoController:queryTracingInfo] begin to query tracing data.");
			log.info("[EmployeeInfoController:queryTracingInfo] { sourceCodeId:"+sourceCodeId+"}.");
			log.info("[EmployeeInfoController:queryTracingInfo] { type:"+type+"}.");
			List<ProduceExecuteLogVo> executeLogVos = executeLogManager.queryLogsByCodeIdAndTypeWe(sourceCodeId, type);
			for (ProduceExecuteLogVo executeLogVo : executeLogVos){
				executeLogVo.setExecuteTime(DateUtil.formatDate(executeLogVo.getExecuteTime()));
				if (executeLogVo.getPic()!=null){
					executeLogVo.setPicArray(executeLogVo.getPic().split(","));
				}
			}
			map.put("executeLogVos", executeLogVos);
			map.put("success", true);	
		} catch (Exception e) {
			map.put("success", false);
			log.error("[EmployeeInfoController:queryTracingInfo] fail to query tracing data.",e);
		}
		return map;
	}
}
