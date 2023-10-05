package com.xuebao.homeworksubmission.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName homework
 */
@TableName(value ="homework")
@Data
public class Homework implements Serializable {
    /**
     * 作业ID，用于表示作业
     */
    @TableId
    private Integer homeworkID;

    /**
     * 作业标题
     */
    private String homeworkTitle;

    /**
     * 作业描述
     */
    private String homeworkComment;

    /**
     * 作业提交的文件类型
     */
    private Object fileType;

    /**
     * 作业提交的截止时间
     */
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}