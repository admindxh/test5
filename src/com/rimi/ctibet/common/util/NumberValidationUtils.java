package com.rimi.ctibet.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字的验证
 */
public class NumberValidationUtils {
      
    private static boolean isMatch(String regex, String orginal){  
        if (orginal == null || orginal.trim().equals("")) {  
            return false;  
        }  
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();  
    }

    /**
     * 正整数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isPositiveInteger(String orginal) {  
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);  
    }

    /**
     * 负整数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isNegativeInteger(String orginal) {  
        return isMatch("^-[1-9]\\d*", orginal);  
    }

    /**
     * 整数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isWholeNumber(String orginal) {  
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);  
    }

    /**
     * 正小数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isPositiveDecimal(String orginal){  
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 负小数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isNegativeDecimal(String orginal){  
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);  
    }

    /**
     * 小数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isDecimal(String orginal){  
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);  
    }

    /**
     * 实数
     * @param orginal 判断的字符串
     * @return true|false
     */
    public static boolean isRealNumber(String orginal){  
        return isWholeNumber(orginal) || isDecimal(orginal);  
    }  
  
}  