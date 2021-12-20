package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("Id")
    private Integer id;

    @TableField("Name")
    private String name;

    @TableField("UserId")
    private Integer userid;

    @TableField("TaskListId")
    private Integer tasklistid;

    @TableField("Description")
    private String description;

    @TableField("StartTime")
    private LocalDateTime starttime;

    @TableField("EndTime")
    private LocalDateTime endtime;

    @TableField("State")
    private Integer state;


}
