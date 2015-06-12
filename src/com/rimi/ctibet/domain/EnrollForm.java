package com.rimi.ctibet.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

/**
 * 报名表单
 */
public class EnrollForm {
	
	/** 属性类型 */
	public static final String PROPERTY_TYPE_TEXT = "text", PROPERTY_TYPE_TEXT_VALUE="文本";//文本
	public static final String PROPERTY_TYPE_NUMBER = "number", PROPERTY_TYPE_NUMBER_VALUE="数字";//数字
	public static final String PROPERTY_TYPE_IMAGE = ".jpg,.jpeg,.png,.bmp", PROPERTY_TYPE_IMAGE_VALUE="图片上传";//图片上传
	public static final String PROPERTY_TYPE_DOC = ".doc,.docx", PROPERTY_TYPE_DOC_VALUE="文档上传";//文档上传
	public static final String PROPERTY_TYPE_SELECT = "select", PROPERTY_TYPE_SELECT_VALUE="下拉选框";//下拉选框
	
	/** 属性类型Map */
	public static final Map<String, String> PROPERTY_TYPE_MAP = getPropertyTypeMap();
	
    private int id;
    private String code;
    private int avaliable=1;
    // 所属活动code
    private String activityCode;
    // 字段
    private String property;
    // 字段类型
    private String propertyType;
    // 字段类型名称
    private String propertyTypeName;
    // 下拉框选项（逗号分隔）
    private String propertyOptions;
    // 是否必填 0否 1是
    private int isRequire;
    // 序号
    private int sortNum;
    
    
    //辅助
    private String[] options;	//选项数
    
    public EnrollForm(){}
    
	@Override
    public String toString() {
        return "EnrollForm [id=" + id + ", code=" + code + ", avaliable="
                + avaliable + ", activityCode=" + activityCode + ", property="
                + property + ", propertyType=" + propertyType
                + ", propertyTypeName=" + propertyTypeName
                + ", propertyOptions=" + propertyOptions + ", isRequire="
                + isRequire + ", sortNum=" + sortNum + ", options="
                + Arrays.toString(options) + ", toString()=" + super.toString()
                + "]";
    }
	
	public static Map<String, String> getPropertyTypeMap(){
		Map<String, String> map = new TreeMap<String, String>();
		map.put(PROPERTY_TYPE_TEXT, PROPERTY_TYPE_TEXT_VALUE);
		map.put(PROPERTY_TYPE_NUMBER, PROPERTY_TYPE_NUMBER_VALUE);
		map.put(PROPERTY_TYPE_IMAGE, PROPERTY_TYPE_IMAGE_VALUE);
		map.put(PROPERTY_TYPE_DOC, PROPERTY_TYPE_DOC_VALUE);
		map.put(PROPERTY_TYPE_SELECT, PROPERTY_TYPE_SELECT_VALUE);
		return map;
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyTypeName() {
		return propertyTypeName;
	}

	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}

	public int getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(int avaliable) {
		this.avaliable = avaliable;
	}

	public String getPropertyOptions() {
		return propertyOptions;
	}

	public void setPropertyOptions(String propertyOptions) {
		this.propertyOptions = propertyOptions;
	}

	public int getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(int isRequire) {
		this.isRequire = isRequire;
	}

	public String[] getOptions() {
		return (options = this.propertyOptions.split(","));
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    //main
	public static void main(String[] args) {
		//System.out.println(new Gson().toJson(getPropertyTypeMap()));
	}
	
}
