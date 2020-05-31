package com.smartfarm.base.farm.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.FarmInfoDao;
import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.farm.core.dao.EmployeeInfoDao;
import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.vo.EmployeeInfoVo;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月29日 16:07:23
 * @version 1.0
 */
@Service("employeeInfoManager")
public class EmployeeInfoImpl implements EmployeeInfoManager {

	@Resource
	private EmployeeInfoDao employeeInfoDao;
	@Resource
	private FarmInfoDao farmInfoDao;
	
	
	@Override
	public EmployeeInfo verifyEmployee(String phone, String password) {
		return employeeInfoDao.getByAcountAndPwd(phone, password);
	}

	@Override
	public List<EmployeeInfo> queryEmployeeListByPage(String name, String phone, Long farmId, Integer start, Integer limit) {
		return employeeInfoDao.getEmployeesPageList(name, phone, farmId, start, limit);
	}

	@Override
	public int queryEmployeesTotal(String name, String phone, Long farmId) {
		return employeeInfoDao.getEmployeesTotal(name, phone, farmId);
	}

	@Override
	public int addEmployee(EmployeeInfo employeeInfo) {
		return employeeInfoDao.insert(employeeInfo);
	}

	@Override
	public EmployeeInfo queryEmployeeById(Long id) {
		return employeeInfoDao.getById(id);
	}

	@Override
	public int modifyEmployeeInfo(EmployeeInfo employeeInfo) {
		return employeeInfoDao.updateById(employeeInfo);
	}

	@Override
	public int deleteEmployeeById(Long id) {
		return employeeInfoDao.deleteById(id);
	}

	@Override
	public EmployeeInfo queryEmployeeInfoByMobile(String mobile, Long farmId) {
		return employeeInfoDao.queryEmployeeInfoByMobile(mobile, farmId);
	}

	@Override
	public List<EmployeeInfo> queryEmployeeInfoByFarmId(Long farmId) {
		return employeeInfoDao.getEmployeeByFarmId(farmId);
	}

}
