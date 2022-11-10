package com.itheima.reggie.utils;

/**
 * @author Administrator
 */
public class HikakuUtils {

    public static boolean isEqual(final Object ob1, final Object ob2) {
        boolean isEqual = false;
        if (ob1 != null && ob2 != null && ob1.getClass().equals(ob2.getClass())) {
            String strOb1 = ob1.toString();
            String strOb2 = ob2.toString();
            isEqual = strOb1.equals(strOb2);
        } else if (ob1 == null && ob2 == null) {
            isEqual = true;
        }
        return isEqual;
    }

    public static boolean isNotEqual(Object ob1, Object ob2) {
        return !isEqual(ob1, ob2);
    }
}
