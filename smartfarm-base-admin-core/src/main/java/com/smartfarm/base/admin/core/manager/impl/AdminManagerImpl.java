package com.smartfarm.base.admin.core.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.admin.core.dao.AdminDao;
import com.smartfarm.base.admin.core.entity.Admin;
import com.smartfarm.base.admin.core.manager.AdminManager;
import com.smartfarm.base.util.MD5Util;

@Service
public class AdminManagerImpl implements AdminManager {
	
	@Resource
    private AdminDao adminDao;

	public int insert(Admin admin) {
		admin.setPassword(MD5Util.MD5X2(admin.getPassword()));
		return adminDao.insert(admin);
	}

	public int updateById(Admin admin) {
		return adminDao.updateById(admin);
	}

	public Admin loginCheck(String userName, String password) {
		Admin admin = adminDao.loginCheck(userName, MD5Util.MD5X2(password));
		if(null == admin) {
			return null;
		}
		Boolean md5Password = MD5Util.MD5X2Verification(password, admin.getPassword());
		if(md5Password) {
			return admin;
		}
		return null;
	}

	public Admin queryAdminById(Long id) {
		return adminDao.queryAdminById(id);
	}



	public List<Admin> queryAdminSearchPage(Integer start, Integer pageSize, Admin admin) {
		return adminDao.querySearchPage(start, pageSize, admin);
	}

	public Integer countAdminSearch(Admin admin) {
		return adminDao.countSearch(admin);
	}
}
