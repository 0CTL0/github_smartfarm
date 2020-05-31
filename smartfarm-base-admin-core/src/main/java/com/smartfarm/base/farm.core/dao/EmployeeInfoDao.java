package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.vo.EmployeeInfoVo;

public interface EmployeeInfoDao {
	
	/**
	 * 根据账号（电话）和密码判断用户是否存在
	 * @param phone
	 * @param password
	 * @return
	 */
	public EmployeeInfo getByAcountAndPwd(@Param("phone") String phone, @Param("password") String password);

	/**
	 * 分页查询员工信息
	 * @param name
	 * @param phone
	 * @param farmId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<EmployeeInfo> getEmployeesPageList(@Param("name") String name, @Param("phone") String phone, @Param("farmId") Long farmId, @Param("start") Integer start, @Param("limit") Integer limit);

	/**
	 * 统计员工数量
	 * @param name
	 * @param phone
	 * @param farmId
	 * @return
	 */
	public int getEmployeesTotal(@Param("name") String name, @Param("phone") String phone, @Param("farmId") Long farmId);

	/**
	 * 添加员工
	 * @param employeeInfo
	 * @return
	 */
	public int insert(EmployeeInfo employeeInfo);

	/**
	 * 根据id查找员工
	 * @param id
	 * @return
	 */
	public EmployeeInfo getById(Long id);

	/**
	 * 修改员工信息
	 * @param employeeInfo
	 * @return
	 */
	public int updateById(EmployeeInfo employeeInfo);

	/**
	 * 删除员工
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    EmployeeInfo queryEmployeeInfoByMobile(@Param("mobile") String mobile, @Param("farmId") Long farmId);

	/**
	 * 根据农场id查询所有员工
	 * @param farmId
	 * @return
	 */
	public List<EmployeeInfo> getEmployeeByFarmId(@Param("farmId") Long farmId);





	int insert2(EmployeeInfo record);

	int updateByPrimaryKey(EmployeeInfo record);
}