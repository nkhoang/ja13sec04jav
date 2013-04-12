package com.hnk.aws.service;

import com.hnk.aws.model.User;
import com.hnk.aws.model.UserGroup;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hnguyen.
 */
@Transactional
public interface UserService {
    public List<UserGroup> listAllGroups();
    public User save(String userName, String password);
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveUsers();
    public List<User> list();
}
