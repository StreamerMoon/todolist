package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
public class Tasklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Id")
    private Integer id;

    @TableField("Name")
    private String name;

    @TableField("UserId")
    private Integer userid;


}
