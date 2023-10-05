package com.xuebao.homeworksubmission.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName homeworkassignments
 */
@TableName(value ="homeworkassignments")
@Data
public class HomeworkAssignments implements Serializable {
    /**
     * 作业ID
     */
    private Integer homeworkID;

    /**
     * 学生ID
     */
    private String userAccount;

    /**
     * 作业提交状态
     */
    private Byte status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}