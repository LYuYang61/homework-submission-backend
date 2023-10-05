package com.xuebao.homeworksubmission.once.importuser;

import com.xuebao.homeworksubmission.mapper.UserMapper;
import com.xuebao.homeworksubmission.model.domain.ClassTableInfo;

import javax.annotation.Resource;
import java.util.List;

public class ClassDAO {

    @Resource
    private UserMapper userMapper;

    public void save(List<ClassTableInfo> list) {
        for (ClassTableInfo data : list) {


        }
    }
}
