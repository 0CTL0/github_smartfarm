package com.smartfarm.base.farm.core.cotroller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.smartfarm.base.util.SessionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.farm.core.entity.EmployeeInfo;
import com.smartfarm.base.farm.core.entity.vo.EmployeeInfoVo;
import com.smartfarm.base.farm.core.manager.EmployeeInfoManager;

@RequestMapping("/farmEmployee")
@Controller
public class FarmEmployeeController {

    private Logger log = Logger.getLogger(FarmEmployeeController.class);

    @Resource
    private EmployeeInfoManager employeeInfoManager;


    /**
     * 查询农场员工
     * @param request
     * @param name
     * @param phone
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/getEmployees")
    @ResponseBody
    public Object getEmployees(HttpServletRequest request, String name, String phone, Integer pageNumber, Integer pageSize){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:getEmployees] begin to query employees by page list.");
            Long farmId = (Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID);
            List<EmployeeInfo> employeeList = employeeInfoManager.queryEmployeeListByPage(name,phone,farmId,(pageNumber - 1) * pageSize, pageSize);
            map.put("employeeList",employeeList);
            map.put("total",employeeInfoManager.queryEmployeesTotal(name, phone, farmId));
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:getEmployees] fail to query employees by page list.",e);
        }
        return  map;
    }

    /**
     * 添加员工
     * @param request
     * @param employeeInfo
     * @return
     */
    @RequestMapping("/addEmployee")
    @ResponseBody
    public Object addEmployee(HttpServletRequest request, EmployeeInfo employeeInfo){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:addEmployee] begin to deal with add employee.");
            employeeInfo.setFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            employeeInfoManager.addEmployee(employeeInfo);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:addEmployee] fail to deal with add employee.",e);
        }
        return  map;
    }

    /**
     * 根据id获取单个员工信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/getSingleEmployee")
    @ResponseBody
    public Object getSingleEmployee(HttpServletRequest request, Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:getSingleEmployee] begin to query employee by id.");
            EmployeeInfo employeeEdit = employeeInfoManager.queryEmployeeById(id);
            map.put("employeeEdit",employeeEdit);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:getSingleEmployee] fail to query employee by id.",e);
        }
        return  map;
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param employeeInfo
     * @return
     */
    @RequestMapping("/updateEmployeeInfo")
    @ResponseBody
    public Object updateEmployeeInfo(HttpServletRequest request, EmployeeInfo employeeInfo){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:updateEmployeeInfo] begin to update employee info.");
            employeeInfoManager.modifyEmployeeInfo(employeeInfo);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:updateEmployeeInfo] fail to update employee info.",e);
        }
        return  map;
    }

    /**
     * 删除员工
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/deleteEmployee")
    @ResponseBody
    public Object deleteEmployee(HttpServletRequest request, Long id){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:deleteEmployee] begin to delete employee.");
            employeeInfoManager.deleteEmployeeById(id);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:deleteEmployee] fail to delete employee.",e);
        }
        return  map;
    }

    /**
     * 查询农场的员工
     * @param request
     * @return
     */
    @RequestMapping("/getFarmEmployees")
    @ResponseBody
    public Object getFarmEmployees(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            log.info("[FarmEmployeeController:getFarmEmployees] begin to query farm's employees .");
            List<EmployeeInfo> employeeList = employeeInfoManager.queryEmployeeInfoByFarmId((Long) SessionUtil.getAttribute(request,SessionUtil.FARM_ID));
            map.put("employeeList", employeeList);
            map.put("success", true);
        }catch (Exception e){
            map.put("success", false);
            log.error("[FarmEmployeeController:getFarmEmployees] fail to query farm's employees.",e);
        }
        return  map;
    }
}
