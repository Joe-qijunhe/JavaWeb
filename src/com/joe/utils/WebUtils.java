package com.joe.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class WebUtils {
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            //将map中的值都注入到对象中
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
    public static int parseInt(String strInt, int defaultValue) {
        if (strInt == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
