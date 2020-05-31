package com.smartfarm.base.admin.core.manager;


import java.util.List;

import com.smartfarm.base.admin.core.entity.Function;

public interface FunctionManager {

    /**
     * 插入
     * @param function
     * @return
     */
    public int insert(Function function);

    /**
     * 更新
     * @param function
     * @return
     */
    public int updateById(Function function);

    /**
     * 根据用户id查询所有的菜单
     * @param id
     * @return
     */
    public List<Function> queryFuncListByAdminId(Long id, Long parentId);

    /**
     * 根据function id查询该function
     * 只提供可修改字段属性
     * @param id
     * @return
     */
    public Function queryFuncById(Long id);

    /**
     * 根据parent_id查询所有function
     * @param id
     * @return
     */
    public List<Function> queryFuncListByParentId(Long id);
}
