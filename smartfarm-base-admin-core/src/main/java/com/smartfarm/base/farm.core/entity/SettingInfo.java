package com.smartfarm.base.farm.core.entity;

public class SettingInfo {

    public final static Short SETTING_SHOP= 1;//商城
    public final static Short SETTING_COMMISSION = 2;//分销
    public final static Short SETTING_RENT=3;//租地
    public final static Short SETTING_CROWD=4;//众筹

    public final static String VALUE_COMMISSION_PATTERN_ENABLE="0";//分佣模式启动
    public final static String VALUE_COMMISSION_PATTERN_DISABLE="1";//分佣模式关闭
    public final static String VALUE_COMMISSION_PATTERN_SKU="1"; //按产品SKU分佣
    public final static String VALUE_COMMISSION_PATTERN_ORDER="2"; //按订单分佣
    public final static String VALUE_COMMISSION_USER_ALL="1"; //全部人参与分销
    public final static String VALUE_COMMISSION_EMPLOYEE_="2"; //仅员工参与分销
    public final static String VALUE_COMMISSION_LEVEL_FIRST="1"; //一级分销
    public final static String VALUE_COMMISSION_LEVEL_SECOND="2"; //二级分销


    /** 流水号 */
    private Long id;
    /** 参数代码 */
    private String paramCode;
    /** 参数名 */
    private String paramName;
    /** 参数值 */
    private String paramValue;
    /** 所属农场 */
    private Long farmId;
    /** 设置类型 */
    private Short settingType;//1：租地；2：其他；3：分销
    /** 参数值备注 */
    private String remark;
    /**页面控件类型*/
    private Short controlType; //1：输入框 2：单选


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Short getSettingType() {
        return settingType;
    }

    public void setSettingType(Short settingType) {
        this.settingType = settingType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Short getControlType() {
        return controlType;
    }

    public void setControlType(Short controlType) {
        this.controlType = controlType;
    }
}