package com.smartfarm.base.admin.core.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smartfarm.base.admin.core.entity.Function;
import com.smartfarm.base.admin.core.manager.FunctionManager;
import com.smartfarm.base.util.SessionUtil;

@Controller
@RequestMapping("/func")
public class FunctionController {
    private static final Logger log = Logger.getLogger(FunctionController.class);
    @Autowired
    private FunctionManager functionManager;

    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(@ModelAttribute Function function) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            functionManager.insert(function);
            log.info("[FunctionController:updateById]insert function.");
            map.put("success", true);
        } catch (Exception e) {
            log.error("[FunctionController:updateById]fail to insert function.");
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object updateById(@ModelAttribute Function function) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            log.info("[FunctionController:updateById]update func by id.");
            functionManager.updateById(function);
            map.put("success", true);
        } catch (Exception e) {
            log.error("[FunctionController:updateById]update func by id.", e);
            map.put("success", false);
        }
        return map;

    }

    /**
     * 查询所有可用菜单
     * @param req
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object listAllFunctions(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long adminId = (Long) SessionUtil.getAttribute(req, SessionUtil.ADMIN_ID);
            log.info("[FunctionController:getAllFunctions]{id=" + adminId + "}query all available functions by admin id.");

            List<Function> rootFunc = functionManager.queryFuncListByAdminId(adminId, null);

            List<Object> root = new ArrayList<Object>();
            for (Function function : rootFunc) {
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("id", function.getId());
                node.put("name", function.getName());
                node.put("code", function.getCode());
                node.put("orderNum", function.getOrderNum());
                node.put("url", function.getUrl());

                List<Function> subFunc = functionManager.queryFuncListByAdminId(adminId, function.getId());
                List<Object> sub = new ArrayList<Object>();
                if (subFunc != null) {
                    for (Function function1 : subFunc) {
                        Map<String, Object> subNode = new HashMap<String, Object>();
                        subNode.put("id", function1.getId());
                        subNode.put("name", function1.getName());
                        subNode.put("code", function1.getCode());
                        subNode.put("orderNum", function1.getOrderNum());
                        subNode.put("url", function1.getUrl());
                        sub.add(subNode);
                    }
                }
                node.put("sub", sub);
                root.add(node);
            }
            map.put("func", root);
            map.put("success", true);
        } catch (Exception e) {
            log.error("[FunctionController:getAllFunctions]false to query all available functions by admin id. " + e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @RequestMapping("/list/{id}")
    @ResponseBody
    public Object listFuncById(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            Function f = functionManager.queryFuncById(id);
            if (f != null) {
                log.info(String.format("[FunctionController:listFuncById]{id=%d}query function by id", id));
                map.put("function", functionManager.queryFuncById(id));
                map.put("success", true);
            } else {
                log.info(String.format("[FunctionController:listFuncById]{id=%d}cannot query function by id: unknown id", id));
                map.put("success", false);
            }
        } catch (Exception e) {
            log.error(String.format("[FunctionController:listFuncById]{id=%d}fail to query function by id. %s", id, e));
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/tree")
    @ResponseBody
    public Object treeFunc() {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            log.info("[FunctionController:listFuncById]tree functions.");
            // 获取根节点
            List<Function> rootFunc = functionManager.queryFuncListByParentId(null);

            ArrayList<Object> root = new ArrayList<Object>();
            for (Function function : rootFunc) {
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("id", function.getId());
                node.put("name", function.getName());

                List<Function> subFunc = functionManager.queryFuncListByParentId(function.getId());
                ArrayList<Object> sub = new ArrayList<Object>();
                if (subFunc != null) {
                    for (Function function1 : subFunc) {
                        Map<String, Object> subNode = new HashMap<String, Object>();
                        subNode.put("id", function1.getId());
                        subNode.put("name", function1.getName());
                        sub.add(subNode);
                    }
                }
                node.put("sub", sub);
                root.add(node);
            }
            map.put("funcTree", root);
            map.put("success", true);
        } catch (Exception e) {
            log.error("[FunctionController:listFuncById]fail to tree functions." + e);
            map.put("success", false);
        }
        return map;
    }
}
