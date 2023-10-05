package com.xuebao.homeworksubmission.service;

import com.xuebao.homeworksubmission.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author lyy
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   学号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @param userName 姓名
     * @param email 邮箱
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String userName, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  学号
     * @param userPassword 密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, String type, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 获取所有的用户账户
     * @return
     */
    List<String> getAllUserAccount();

    String getUserFileName(int userId);
}
