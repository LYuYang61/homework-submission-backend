package com.xuebao.homeworksubmission.model.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 班级同学信息 将表格和对象相关联起来
 *
 * @author lyy
 */
@Data
public class ClassTableInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 学号
     */
    @ExcelProperty("学号")
    private String userAccount;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String userName;

}
