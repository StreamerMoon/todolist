package com.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2021-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private   String   token;

    /**
     * 用户的id
     */
    private String uuid;

    /**
     * 用户的名字
     */
    private String username;

    /**
     * 用户的密码
     */
    private String password;

    /**
     * 用户的头像
     */
    private String avatar;

    /**
     * 用户的手机号
     */
    private String phone;

    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的城市
     */
    private String city;

    /**
     * 用户的状态
     */
    private Integer status;

    /**
     * 1是正常，2是锁定
     */
    private Integer islock;

    /**
     * 创建时间
     */
    private LocalDateTime createat;

    private String code;


}
