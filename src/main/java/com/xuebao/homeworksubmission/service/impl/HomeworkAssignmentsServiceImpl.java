package com.xuebao.homeworksubmission.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuebao.homeworksubmission.mapper.HomeworkAssignmentsMapper;
import com.xuebao.homeworksubmission.mapper.HomeworkMapper;
import com.xuebao.homeworksubmission.mapper.UserMapper;
import com.xuebao.homeworksubmission.model.domain.Homework;
import com.xuebao.homeworksubmission.model.domain.HomeworkAssignments;
import com.xuebao.homeworksubmission.model.domain.User;
import com.xuebao.homeworksubmission.service.HomeworkAssignmentsService;
import com.xuebao.homeworksubmission.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 *
 */
@Service
public class HomeworkAssignmentsServiceImpl extends ServiceImpl<HomeworkAssignmentsMapper, HomeworkAssignments>
    implements HomeworkAssignmentsService {

    @javax.annotation.Resource
    private HomeworkAssignmentsMapper homeworkAssignmentsMapper;

    @javax.annotation.Resource
    private UserMapper userMapper;

    @javax.annotation.Resource
    private HomeworkMapper homeworkMapper;

    @Value("${submit.path}")
    private String path;

    @Override
    public boolean homeworkRelease(List<String> userAccountList, int homeworkID) {
        HomeworkAssignments homeworkAssignments = null;
        for(String userAccount : userAccountList){
            homeworkAssignments = new HomeworkAssignments();
            homeworkAssignments.setUserAccount(userAccount);
            homeworkAssignments.setHomeworkID(homeworkID);
            homeworkAssignments.setStatus((byte)0);
            boolean save = this.save(homeworkAssignments);
            if(!save)
                return false;
        }
        return true;
    }

    @Override
    public boolean submit(int homeworkID, String userAccount, MultipartFile file2Submit) {
        LambdaUpdateWrapper<HomeworkAssignments> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(HomeworkAssignments::getHomeworkID,homeworkID)
                .like(HomeworkAssignments::getUserAccount, userAccount);
        String fileName = file2Submit.getOriginalFilename();
        String account = fileName.substring(0, 10);
        // 获取文件夹名称（作业标题）
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homeworkID", homeworkID);
        Homework homework = homeworkMapper.selectOne(queryWrapper);
        String homeworkTitle = homework.getHomeworkTitle();
        if(userAccount.equals(account)){
            StringBuilder sb = new StringBuilder(path);
            sb.append("\\" + homeworkTitle);
            File directory = new File(sb.toString());
            if(!directory.exists()){
                directory.mkdir();
            }
            sb.append("\\" + fileName);
            File file = new File(sb.toString());
            // TODO 重复上传时覆盖原文件
            try {
                file2Submit.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }
        // 作业提交完成后，才修改状态
        HomeworkAssignments homeworkAssignments = new HomeworkAssignments();
        homeworkAssignments.setStatus((byte)1);
        return this.update(homeworkAssignments, updateWrapper);
    }

    @Override
    public Resource download(int homeworkID) {
        // 如果result目录不存在则创建
        File resultDirectory = new File(path + "//result");
        if(!resultDirectory.exists()){
            resultDirectory.mkdir();
        }
        QueryWrapper<Homework> queryWrapper = new QueryWrapper<Homework>().eq("homeworkID", homeworkID);
        Homework homework = homeworkMapper.selectOne(queryWrapper);
        String homeworkTitle = homework.getHomeworkTitle();
        StringBuilder sb = new StringBuilder(path);
        sb.append("\\result\\" + homeworkTitle + ".zip");
        String zipPath = sb.toString();
        sb = new StringBuilder(path);
        sb.append("\\" + homeworkTitle);
        String homeworkFolder = sb.toString();
        ZipOutputStream zos = null;
        try {
            File zipFile = new File(zipPath);
            if(zipFile.exists()){
                zipFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(new File(zipPath));
            ZipUtils.toZip(homeworkFolder, outputStream, true);
            Path path = Paths.get(zipPath);
            UrlResource resource = new UrlResource(path.toUri());
            if(resource.exists() && resource.isReadable()){
                return resource;
            }
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }/*
        try{
            zos = new ZipOutputStream();
        }
        */
        return null;
    }

    @Override
    public List<User> CheckForCompletion(int homeworkID) {
        List<User> users = new ArrayList<>();
        QueryWrapper<HomeworkAssignments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("homeworkID", homeworkID)
                .eq("status", 0);
        List<HomeworkAssignments> unfinishedUsers = homeworkAssignmentsMapper.selectList(queryWrapper);
        for(HomeworkAssignments unfinishedUser : unfinishedUsers){
            String userAccount = unfinishedUser.getUserAccount();
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().eq("userAccount", userAccount);
            User user = userMapper.selectOne(userQueryWrapper);
            users.add(user);
        }
        return users;
    }


}




