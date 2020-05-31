package com.smartfarm.base.admin.core.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.admin.core.entity.Admin;

public interface AdminDao {
	
	/**
	 * 添加
	 * @param admin
	 * @return
	 */
    public int insert(Admin admin);

    /**
     * 根据id修改
     * @param admin
     * @return
     */
    public int updateById(Admin admin);
    
    /**
     * 查询登录
     * @param account
     * @param password
     * @return
     */
    public Admin loginCheck(@Param("account") String account, @Param("password") String password);
    
    /**
     * 根据id查询AdminVo
     * @param id
     * @return
     */
    public Admin queryAdminById(Long id);


    /**
     * page query with search keys
     * @param start
     * @param pageSize
     * @param adminVo
     * @return
     */
    List<Admin> querySearchPage(@Param("start") Integer start,
                                @Param("pageSize") Integer pageSize,
                                @Param("adminVo") Admin admin);

    /**
     * count with search keys
     * @param adminVo
     * @return
     */
    Integer countSearch(@Param("adminVo") Admin admin);
}