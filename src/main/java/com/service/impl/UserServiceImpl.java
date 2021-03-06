package com.service.impl;

import com.dao.UserMapper;
import com.domain.User;
import com.exception.UserException;
import com.service.UserService;
import com.util.SendJMail;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userDao;

    public void register(User newUser) throws UserException {
        try {
            userDao.addUser(newUser);
            String emailMsg = "注册成功,请<a href = 'http://192.168.1.105:8080/mybookstore/user?method=activeCode&activeCode=\"+ user.getActiveCode()+\"'>激活</a>后登录";
            SendJMail.sendMail(newUser.getEmail(), emailMsg );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("注册失败");
        }
    }

    public void activerUser(String activeCode) throws UserException {
        try {
            User user = userDao.findUserByActiveCode(activeCode);
            if(user != null) {
                // �жϼ������Ƿ����
                userDao.activeUser(user.getId());
            } else {
                throw new UserException("�û�����ʧ�ܣ�");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û�����ʧ�ܣ�");
        }
    }

    public User login(String username, String password) throws UserException {
        try {
            return userDao.findUserByUserNameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û���¼ʧ�ܣ�");
        }
    }

    public void modifyUserInfo(User loginUser) throws UserException {
        try {
            userDao.modifyUserInfo(loginUser);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�û���Ϣ�޸�ʧ�ܣ����Ժ����ԣ�");
        }
    }

    public User findUserByEmail(String email) throws UserException {
        try {
            return userDao.findUserByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�����û�ʧ�ܣ�");
        }
    }

    public User findUserByUsername(String username) throws UserException {
        try {
            return userDao.findUserByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("�����û�ʧ�ܣ�");
        }
    }

}
