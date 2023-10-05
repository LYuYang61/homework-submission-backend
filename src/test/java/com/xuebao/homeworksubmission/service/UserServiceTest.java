package com.xuebao.homeworksubmission.service;

import com.xuebao.homeworksubmission.model.domain.User;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author lyy
 */

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("廉宇阳");
        user.setUserAccount("2021218247");
        user.setUserPassword("12345678");
        user.setEmail("2242727324@qq.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("廉宇阳");
        user.setUserAccount("2021218247");
        user.setUserPassword("123456789");
        user.setEmail("2242727324@qq.com");
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void testGetUser() {
        User user = userService.getById(1L);
        Assertions.assertNotNull(user);
    }

    @Test
    public void userRegister() {
        String userAccount = "2021218252";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String userName = "吴柳航";
        String email = "3139651094@qq.com";
        long result = userService.userRegister(userAccount, userPassword, userName, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}
