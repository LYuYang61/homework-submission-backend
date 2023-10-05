package com.xuebao.homeworksubmission.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.xuebao.homeworksubmission.common.BaseResponse;
import com.xuebao.homeworksubmission.common.ErrorCode;
import com.xuebao.homeworksubmission.common.ResultUtils;
import com.xuebao.homeworksubmission.mapper.HomeworkMapper;
import com.xuebao.homeworksubmission.model.domain.Homework;
import com.xuebao.homeworksubmission.model.domain.request.HomeworkRequest;
import com.xuebao.homeworksubmission.service.HomeworkService;
import com.xuebao.homeworksubmission.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "作业")
@RequestMapping("/homework")
@CrossOrigin
public class HomeworkController {

    @Resource
    HomeworkService homeworkService;
    @Resource
    HomeworkMapper homeworkMapper;

    @ApiOperation("发布作业")
    @PostMapping("/release")
    public BaseResponse<Boolean> publishHomework(@RequestBody HomeworkRequest homeworkRequest){
        System.out.println(homeworkRequest);
        if(homeworkRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String homeworkTitle = homeworkRequest.getHomeworkTitle();
        String homeworkComment = homeworkRequest.getHomeworkComment();
        String fileType = homeworkRequest.getFileType();
        String endTime = homeworkRequest.getEndTime();
        Homework homework = new Homework();
        homework.setHomeworkID(null);
        homework.setHomeworkTitle(homeworkTitle);
        homework.setHomeworkComment(homeworkComment);
        homework.setFileType(fileType);
        homework.setEndTime(DateUtils.StringToDate(endTime));
        boolean isPublished = homeworkService.publishHomework(homework);
        return ResultUtils.success(isPublished);
    }

    @ApiOperation("编辑作业")
    @PostMapping("/edit")
    public BaseResponse<Boolean> editHomework(@RequestBody HomeworkRequest homeworkRequest){
        if(homeworkRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String homeworkIDString = homeworkRequest.getHomeworkID();
        String homeworkTitle = homeworkRequest.getHomeworkTitle();
        String homeworkComment = homeworkRequest.getHomeworkComment();
        String fileType = homeworkRequest.getFileType();
        String endTime = homeworkRequest.getEndTime();
        Homework homework = new Homework();
        int homeworkID = Integer.parseInt(homeworkIDString);
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<Homework>().eq("homeworkID", homeworkID);
        Homework homework1 = homeworkMapper.selectOne(queryWrapper);
        String oldTitle = homework1.getHomeworkTitle();
        homework.setHomeworkID(homeworkID);
        homework.setHomeworkTitle(homeworkTitle);
        homework.setHomeworkComment(homeworkComment);
        homework.setFileType(fileType);
        homework.setEndTime(DateUtils.StringToDate(endTime));
        boolean success = homeworkService.editHomework(homework, oldTitle);
        return ResultUtils.success(success);
    }

    @ApiOperation("删除作业")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteHomework(@RequestBody HomeworkRequest homeworkRequest){
        String homeworkIDString = homeworkRequest.getHomeworkID();
        int homeworkID = Integer.parseInt(homeworkIDString);
        return ResultUtils.success(homeworkService.deleteHomework(homeworkID));
    }

    @ApiOperation("获取全部的作业")
    @PostMapping("/getHomeworkList")
    public BaseResponse<List<Homework>> getAllHomework(){
        List<Homework> homeworks = homeworkMapper.selectList(new QueryWrapper<>());
        return ResultUtils.success(homeworks);
    }
//    public String getHomeworkFolderName(Timestamp homeworkID){
//        return homeworkService.getHomeworkFolderName(homeworkID);
//    }
}
