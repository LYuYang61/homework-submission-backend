package com.xuebao.homeworksubmission.controller;

import com.xuebao.homeworksubmission.common.BaseResponse;
import com.xuebao.homeworksubmission.common.ErrorCode;
import com.xuebao.homeworksubmission.common.ResultUtils;
import com.xuebao.homeworksubmission.model.domain.User;
import com.xuebao.homeworksubmission.model.domain.request.HomeworkSubmitRequest;
import com.xuebao.homeworksubmission.service.HomeworkAssignmentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "提交作业")
@RequestMapping("/homework")
@CrossOrigin
public class HomeworkAssignmentsController {

    @Resource
    private HomeworkAssignmentsService homeworkAssignmentsService;

    @ApiOperation("提交作业文件")
    @PostMapping("/submit")
    public BaseResponse<Boolean> submit(@ModelAttribute HomeworkSubmitRequest homeworkSubmitRequest, @RequestParam("uploadFile") MultipartFile file2Submit) {
        if(homeworkSubmitRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int homeworkID = Integer.parseInt(homeworkSubmitRequest.getHomeworkID());
        String id = homeworkSubmitRequest.getUserAccount();
        if(StringUtils.isAnyBlank(id)){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        boolean submit = homeworkAssignmentsService.submit(homeworkID, id, file2Submit);
        return ResultUtils.success(submit);
    }

    @ApiOperation("下载作业压缩包")
    @GetMapping("/download/{homeworkID}")
    public ResponseEntity<org.springframework.core.io.Resource> download(@PathVariable int homeworkID){
        org.springframework.core.io.Resource homeworkZIP = homeworkAssignmentsService.download(homeworkID);
        if (homeworkZIP == null) {
            return ResponseEntity.notFound().build(); // 如果没有找到文件
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + homeworkZIP.getFilename() + "\"")
                .body(homeworkZIP); // 返回文件内容
    }

    @ApiOperation("查看未提交名单")
    @PostMapping("/check")
    public BaseResponse<List<User>> checkForCompletion(@RequestBody HomeworkSubmitRequest homeworkSubmitRequest){
        String homeworkIDString = homeworkSubmitRequest.getHomeworkID();
        System.out.println("啦啦啦啦homeworkID:" + homeworkIDString);
        int homeworkID = Integer.parseInt(homeworkIDString);
        List<User> users = homeworkAssignmentsService.CheckForCompletion(homeworkID);
        return ResultUtils.success(users);
    }
}
