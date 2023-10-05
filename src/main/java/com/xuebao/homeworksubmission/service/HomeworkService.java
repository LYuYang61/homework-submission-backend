package com.xuebao.homeworksubmission.service;

import com.xuebao.homeworksubmission.model.domain.Homework;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 */
public interface HomeworkService extends IService<Homework> {
    /**
     * 添加作业
     * @param homework
     * @author cjd
     */
    boolean publishHomework(Homework homework);

    /**
     * 删除作业
     * @param homeworkID
     * @author cjd
     * @return
     */
    boolean deleteHomework(int homeworkID);

    boolean editHomework(Homework homework, String oldTitle);

}
