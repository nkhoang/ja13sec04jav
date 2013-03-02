package com.hnk.aws.repository;

import com.hnk.aws.dao.impl.BaseDaoImpl;
import com.hnk.aws.dao.UserDao;
import com.hnk.aws.model.QUser;
import com.hnk.aws.model.User;
import org.jasypt.spring.security3.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class UserRepositoryImpl extends BaseDaoImpl implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * load user by username.
     *
     * @param username
     * @return
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QUser user = QUser.user;
        User foundUser = getQuery().from(user).where(user.username.eq(username)).uniqueResult(user);
        if (foundUser == null) {
            throw new UsernameNotFoundException(String.format("Could not find username: %s", username));
        }
        return foundUser;
    }
}
