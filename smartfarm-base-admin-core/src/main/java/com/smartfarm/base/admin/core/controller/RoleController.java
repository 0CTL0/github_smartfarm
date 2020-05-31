package com.smartfarm.base.admin.core.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.admin.core.entity.Role;
import com.smartfarm.base.admin.core.entity.RoleFunction;
import com.smartfarm.base.admin.core.manager.AdminRoleManager;
import com.smartfarm.base.admin.core.manager.RoleFunctionManager;
import com.smartfarm.base.admin.core.manager.RoleManager;

@Controller
@RequestMapping("/role")
public class RoleController {
    private static final Logger log = Logger.getLogger(RoleController.class);
    @Autowired
    private AdminRoleManager adminRoleManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private RoleFunctionManager roleFunctionManager;

    /**
     * 列出所有的role
     * @param req
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object listRole(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[RoleController:listRole]list all role");
            map.put("roleList", roleManager.queryRoleList());
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:listRole]fail to list all role." + e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 根据id列出所有的role
     * @param id
     * @return
     */
    @RequestMapping("/list/{id}")
    @ResponseBody
    public Object queryRoleById(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[RoleController:listRole]{id="+ id +"}query all role by id");
            map.put("list", adminRoleManager.queryRoleByAdminId(id));
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:listRole]fail to query all role by id. " + e);
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object updateRole(Long id, String name, String description) {
        Map<String, Object> map = new HashMap<String, Object>();
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setDescription(description);

        try {
            log.info("[RoleController:updateRole]{id="+ id +",name="+ name +",description="+ description +"}update role.");
            roleManager.updateRole(role);
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:updateRole]{id="+ id +",name="+ name +",description="+ description +"}update role. " + e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 分页查询所有供应商
     */
    @RequestMapping("/queryPage")
    @ResponseBody
    public Object queryPage(Integer pageSize, Integer pageNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[RoleController:queryPage]page query all role.");
            map.put("roles",roleManager.queryAllPage((pageNumber - 1) * pageSize, pageSize));
            map.put("total", roleManager.countAllPage());
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:queryPage]fail to page query all role.",e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 添加Role
     * @param name
     * @param description
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object insertRole(String name, String description) {
        Map<String, Object> map = new HashMap<String, Object>();

        Role role = new Role();
        role.setName(name);
        role.setDescription(description);

        try {
            log.info("[RoleController:insertRole]{name="+ name +",description="+ description +"}insert role.");
            roleManager.insert(role);
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:updateRole]{name="+ name +",description="+ description +"}insert role. " + e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 根据roleId查询func
     * @param id
     * @return
     */
    @RequestMapping("/func")
    @ResponseBody
    public Object listRoleFunc(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            log.info("[RoleController:listRoleFunc]{id=" + id + "}list func by role id.");
            map.put("funcContained", roleFunctionManager.queryByRoleId(id));
            map.put("success", true);
        } catch (Exception e) {
            log.error("[RoleController:listRoleFunc]fail to list func by role id. " + e);
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/func/update.htm")
    @ResponseBody
    public Object updateRoleFunc(Long id, @RequestParam(value = "func[]") Long[] func) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[RoleController:updateRoleFunc]{id=" + id + ", func=" + Arrays.toString(func) +"}update func by role id.");
            roleFunctionManager.deleteByRoleId(id);

            for (Long aLong : func) {
                RoleFunction roleFunction = new RoleFunction();
                roleFunction.setRoleId(id);
                roleFunction.setFunctionId(aLong);
                roleFunctionManager.insert(roleFunction);
            }
            map.put("success", true);
        } catch (Exception e) {
            log.info("[RoleController:updateRoleFunc]fail to update func by role id." + e);
            map.put("success", false);
        }
        return map;
    }

    
}
