package com.hnk.aws.service.impl;

import com.hnk.aws.model.User;
import com.hnk.aws.model.UserGroup;
import com.hnk.aws.model.UserLog;
import com.hnk.aws.repository.UserGroupRepository;
import com.hnk.aws.repository.UserLogRepository;
import com.hnk.aws.repository.UserRepository;
import com.hnk.aws.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.jasypt.spring.security3.PasswordEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author hnguyen.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserLogRepository userLogRepository;
    @Override
    public List<UserGroup> listAllGroups() {
        return userGroupRepository.listAllGroups();
    }

    @Override
    public User save(String userName, String password) {
        User u = new User();
        u.setBirthDate(new DateTime());
        u.setPassword(passwordEncoder.encodePassword(password, null));

        User savedU = userRepository.save(u);
        // save log
        UserLog log = new UserLog();
        log.setUserId(savedU.getId());
        log.setCreatedDate(new Date());

        userLogRepository.save(log);
        return savedU;
    }

    public void saveUsers() {
        for (int i = 0 ;i < 10; i++) {
            save("nkhoang.it", "password");
        }
        User user = userRepository.findOne(1L);
        userRepository.save(user);
    }

    public List<User> list() {
        List<User> users = new ArrayList<User>();
        Iterator<User> userIterator = userRepository.findAll().iterator();
        while (userIterator.hasNext()) {
            users.add(userIterator.next());
        }
        return users;
    }
}
