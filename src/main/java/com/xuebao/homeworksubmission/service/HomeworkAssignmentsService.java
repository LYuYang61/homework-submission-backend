package com.xuebao.homeworksubmission.service;

import com.xuebao.homeworksubmission.model.domain.HomeworkAssignments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuebao.homeworksubmission.model.domain.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 *
 *
 */
public interface HomeworkAssignmentsService extends IService<HomeworkAssignments> {
    /**
     * 发布作业
     * @param userAccountList
     * @param homeworkID
     * @return
     */
    boolean homeworkRelease(List<String> userAccountList, int homeworkID);

    /**
     * 提交作业
     * @param homeworkID
     * @param userAccount
     * @param file
     * @return
     */
    boolean submit(int homeworkID, String userAccount, MultipartFile file);

    /**
     * 下载作业
     * @param homeworkID
     * @return
     */
    Resource download(int homeworkID);

    List<User> CheckForCompletion(int homeworkID);
}
