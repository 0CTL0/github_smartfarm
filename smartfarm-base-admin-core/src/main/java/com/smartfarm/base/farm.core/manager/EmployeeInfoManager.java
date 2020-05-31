package com.smartfarm.base.farm.core.manager;

import java.util.List;

import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.vo.EmployeeInfoVo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年11月29日 16:06:05
 * @version 1.0
 */
public interface EmployeeInfoManager {

	/**
	 * 账号密码验证用户是否存在
	 * @param phone
	 * @param password
	 * @return
	 */
	public EmployeeInfo verifyEmployee(String phone, String password);

	/**
	 * 按条件分页查询员工信息
	 * @param name
	 * @param phone
	 * @param farmId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<EmployeeInfo> queryEmployeeListByPage(String name, String phone, Long farmId, Integer start, Integer limit);

	/**
	 * 按条件统计员工数量
	 * @param name
	 * @param phone
	 * @param farmId
	 * @return
	 */
	public int queryEmployeesTotal(String name, String phone, Long farmId);

	/**
	 * 添加员工
	 * @param employeeInfo
	 * @return
	 */
	public int addEmployee(EmployeeInfo employeeInfo);

	/**
	 * 查找员工
	 * @param id
	 * @return
	 */
	public EmployeeInfo queryEmployeeById(Long id);

	/**
	 * 修改员工信息
	 * @param employeeInfo
	 * @return
	 */
	public int modifyEmployeeInfo(EmployeeInfo employeeInfo);

	/**
	 * 根据id删除员工
	 * @param id
	 * @return
	 */
	public int deleteEmployeeById(Long id);
	
	/**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    EmployeeInfo queryEmployeeInfoByMobile(String mobile, Long farmId);

	/**
	 * 查询农场的所有员工
	 * @param farmId
	 * @return
	 */
	public List<EmployeeInfo> queryEmployeeInfoByFarmId(Long farmId);
}
