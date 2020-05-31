package com.smartfarm.base.admin.core.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.admin.core.entity.Function;

public interface FunctionDao {

	/**
	 * 添加
	 * @param function
	 * @return
	 */
    public int insert(Function function);

    /**
     * 修改
     * @param function
     * @return
     */
    public int updateById(Function function);

	/**
	 * 根据用户id查询所有的菜单
	 * @param id
	 * @return
	 */
	public List<Function> queryFuncListByAdminId(@Param("id") Long id,
                                                 @Param("parentId") Long parentId);

	/**
	 * 根据function id查询该function
	 * 只提供可修改字段属性
	 * @param id
	 * @return
	 */
	public Function queryFuncById(Long id);

	/**
	 * 根据parent_id查询所有function
	 * @param parentId
	 * @return
	 */
	public List<Function> queryFuncListByParentId(@Param("parentId") Long parentId);
}