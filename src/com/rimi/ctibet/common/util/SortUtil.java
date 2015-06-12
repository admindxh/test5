/**
 * 
 */
package com.rimi.ctibet.common.util;

/**
 * @author liuxz
 * @date 2014-3-4
 * @package com.rimi.medical.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class SortUtil {
	public static String getSortCondition(String obj,String field,String sort){
		return " order by "+obj+"."+field+" "+sort;
	}
	public static void main(String[] args) {
		//System.out.println(getSortCondition("obj", "field", "sort"));
	}
}
