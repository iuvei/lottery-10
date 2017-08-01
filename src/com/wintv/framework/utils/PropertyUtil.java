package com.wintv.framework.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Steady Wang
 */
public class PropertyUtil {
    private Log log = LogFactory.getLog(PropertyUtil.class);

    /**
     * 用于转换 Ext 的 Checkbox 传回的 Value -> Boolean: on/off/null
     *
     * @param stringValue
     * @return
     */
    public static boolean stringToBoolean(String stringValue) {
        if (StringUtils.isEmpty(stringValue)) {
            return false;
        } else if ("on".equalsIgnoreCase(stringValue)) {
            return true;
        } else if ("off".equalsIgnoreCase(stringValue)) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 用于转换 Boolean 的值为 Ext 可用的值
     *
     * @param boolValue
     * @return
     */
    public static String booleanToString(Boolean boolValue) {
        return (boolValue != null && boolValue) ? "on" : "off";
    }
}
