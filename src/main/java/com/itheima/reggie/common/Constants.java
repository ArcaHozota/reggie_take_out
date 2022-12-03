package com.itheima.reggie.common;



/**
 * 項目常量定義類
 *
 * @author Administrator
 */
public final class Constants {

    /**
     * 未登錄狀態
     */
    public static final String NOT_LOGIN = "NOT_LOGIN";

    /**
     * 登錄失敗
     */
    public static final String LOGIN_FAILED = "LOGIN_FAILED";

    /**
     * 項目等待超時
     */
    public static final String TIME_OUT = "TIMEOUT";

    /**
     * 賬號被禁用
     */
    public static final String FORBIDDEN = "ACCOUNT_PROHIBITED";

    /**
     * 初始密碼
     */
    public static final String PRIMARY_CODE = "111111";

    /**
     * 出現異常
     */
    public static final String ERROR = "ERROR_OCCURRED";

    /**
     * 重複的主鍵值
     */
    public static final String DUPLICATED_KEY = "Duplicate entry";

    /**
     * 沒有相對應的結果
     */
    public static final String NO_CONSEQUENCE = "NO_SUCH_CONSEQUENCES";

    /**
     * 獲取Entity類名
     *
     * @param obj
     * @return class_name
     */
    public static String getEntityName(Object obj) {
        return obj.getClass().getSimpleName().toLowerCase();
    }

}
