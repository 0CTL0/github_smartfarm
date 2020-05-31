package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.farm.core.entity.SourceCode;
import com.smartfarm.base.farm.core.entity.vo.SourceCodeVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.SourceInfo;
import com.smartfarm.base.farm.core.manager.SourceCodeManager;
import com.smartfarm.base.farm.core.manager.SourceInfoManager;

/**
 * 溯源层
 * @author lyq
 *
 */
@Controller
@RequestMapping("/source")
public class SourceController {
	
	private Logger log = Logger.getLogger(SourceController.class);
	
	
	@Resource
	private SourceInfoManager sourceInfoManager;
	
	@Resource
	private SourceCodeManager sourceCodeManager;
	
	
	/**
	 * 查询溯源信息列表
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/qurySourceInfoList")
	@ResponseBody
	public Object qurySourceInfoList(HttpServletRequest request,String name,String prefix,Integer pageSize, Integer pageNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[SourceController:qurySourceInfoList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}query sourceInfo for page.");
        	Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
        	map.put("sourceInfoList", sourceInfoManager.qurySourceInfoList(farmId,name,prefix,(pageNumber - 1) * pageSize, pageSize));
			map.put("total", sourceInfoManager.quryCountSourceInfoList(farmId));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[SourceController:qurySourceInfoList]false to query sourceInfo count.", e);
		}
		return map;
	}
	
	/**
	 * 新增溯源信息
	 * @param sourceInfo
	 * @return
	 */
	@RequestMapping("/addSourceInfo")
	@ResponseBody
	public Object addSourceInfo(SourceInfo sourceInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[SourceController:addSourceInfo]insert sourceInfo for page.");
        	if(sourceInfoManager.addSourceInfo(sourceInfo)<1){
        		map.put("success", false);
        		return map;
        	}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[SourceController:addSourceInfo]false to insert sourceInfo.", e);
		}
		return map;
	}
	
	/**
	 * 查询溯源码列表
	 * @param request
	 * @param name
	 * @param code
	 * @param status
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/querySourceCodeList")
	@ResponseBody
	public Object querySourceCodeList(HttpServletRequest request,String name, String code, Short status,Integer pageSize, Integer pageNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
        	log.info("[SourceController:querySourceCodeList]{pageSize:" + pageSize + ",name:" + name + ",code:"+ code + "status:" + status + ",pageNumber:" + pageNumber + "}query sourceCode for page.");
			Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
			List<SourceCodeVo> sourceCodeList = sourceCodeManager.querySourceCodeList(farmId,name, code, status, (pageNumber - 1) * pageSize, pageSize);
			for (SourceCodeVo codeVo : sourceCodeList){
				if (codeVo.getFirstSearchTime()!=null){
					codeVo.setFirstSearchTime(DateUtil.formatDateTime(codeVo.getFirstSearchTime()));
				}
				codeVo.setCreateTime(DateUtil.formatDateTime(codeVo.getCreateTime()));
			}
			map.put("sourceCodeList", sourceCodeList);
			map.put("total", sourceCodeManager.queryCountSourceCodeList(farmId,name, code, status));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("[SourceController:querySourceCodeList]false to query sourceCode count.", e);
		}
		return map;
	}
	
}
