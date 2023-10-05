package com.xuebao.homeworksubmission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuebao.homeworksubmission.common.ErrorCode;
import com.xuebao.homeworksubmission.exception.BusinessException;
import com.xuebao.homeworksubmission.mapper.HomeworkAssignmentsMapper;
import com.xuebao.homeworksubmission.model.domain.Homework;
import com.xuebao.homeworksubmission.model.domain.HomeworkAssignments;
import com.xuebao.homeworksubmission.service.HomeworkAssignmentsService;
import com.xuebao.homeworksubmission.service.HomeworkService;
import com.xuebao.homeworksubmission.mapper.HomeworkMapper;
import com.xuebao.homeworksubmission.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 *
 */
@Service
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework>
    implements HomeworkService{
    @Resource
    UserService userService;

    @Resource
    HomeworkMapper homeworkMapper;

    @Resource
    HomeworkAssignmentsMapper homeworkAssignmentsMapper;

    @Resource
    HomeworkAssignmentsService homeworkAssignmentsService;

    @Value("${submit.path}")
    String path;

    @Override
    public boolean publishHomework(Homework homework) {
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homeworkTitle", homework.getHomeworkTitle());
        Long count = homeworkMapper.selectCount(queryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作业重复");
        }
        System.out.println(homework);
        boolean saveResult = this.save(homework);
        Integer homeworkID = homework.getHomeworkID();
        List<String> allUserAccount = userService.getAllUserAccount();
        homeworkAssignmentsService.homeworkRelease(allUserAccount, homeworkID);
        if(!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作业发布失败");
        }
        return true;
    }

    @Override
    public boolean deleteHomework(int homeworkID) {
        boolean remove = this.removeById(homeworkID);
        QueryWrapper<HomeworkAssignments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homeworkID", homeworkID);
        int delete = homeworkAssignmentsMapper.delete(queryWrapper);
        if(!remove && delete != 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作业删除失败");
        }
        return remove;
    }

    @Override
    public boolean editHomework(Homework homework, String oldTitle) {
        Integer homeworkID = homework.getHomeworkID();
        Homework homework2Edit = this.getById(homeworkID);
        homework2Edit.setHomeworkTitle(homework.getHomeworkTitle());
        homework2Edit.setHomeworkComment(homework.getHomeworkComment());
        homework2Edit.setFileType(homework.getFileType());
        homework2Edit.setEndTime(homework.getEndTime());
        // 修改对应文件夹名称
        StringBuilder sb = new StringBuilder(path);
        sb.append("//" + oldTitle);
        File oldDirectory = new File(sb.toString());
        if(oldDirectory.exists()){
            sb = new StringBuilder(path);
            sb.append("//" + homework.getHomeworkTitle());
            oldDirectory.renameTo(new File(sb.toString()));
        }
        boolean success = this.updateById(homework2Edit);
        return success;
    }
}




