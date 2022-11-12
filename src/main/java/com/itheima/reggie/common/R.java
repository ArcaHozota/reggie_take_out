package com.itheima.reggie.common;


import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回結果類
 *
 * @author Administrator
 */
@Data
public class R<T> {

    /**
     * 編碼：
     * 成功：1
     * 失敗：0或其它字符
     */
    private Integer code;

    /**
     * 錯誤信息
     */
    private String msg;

    /**
     * 數據
     */
    private T data;

    /**
     * 動態數據
     */
    private Map<String, Object> map = new HashMap<>();

    public static <T> R<T> success(@NonNull T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(@NonNull String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
