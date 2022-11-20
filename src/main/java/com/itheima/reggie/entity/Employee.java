package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 員工管理實體類
 *
 * @author Administrator
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    private String username;

    private String name;

    private String password;

    @TableField(value = "phone_number")
    private String phoneNo;

    @TableField(value = "sex")
    private String gender;

    private String idNumber;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
