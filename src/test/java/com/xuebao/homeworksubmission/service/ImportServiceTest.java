package com.xuebao.homeworksubmission.service;

import com.alibaba.excel.EasyExcel;
import com.xuebao.homeworksubmission.model.domain.ClassTableInfo;

import com.xuebao.homeworksubmission.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ImportServiceTest {

    @Resource
    private UserService userService;

    private static final String SALT = "xuebao";

    @Test
    public void testAddUser() {
        String fileName = "src\\main\\resources\\计科21-5班.xlsx";
        // 默认密码
        String userPassword = "12345678";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<ClassTableInfo> list = EasyExcel.read(fileName).head(ClassTableInfo.class).sheet().doReadSync();
        for (ClassTableInfo data : list) {
            User user = new User();
            user.setId(data.getId());
            user.setUserName(data.getUserName());
            user.setUserAccount(data.getUserAccount());
            user.setUserPassword(encryptPassword);
            boolean result = userService.save(user);
            System.out.println(user.getId());
            Assertions.assertTrue(result);
        }
    }
}
