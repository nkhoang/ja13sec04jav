package com.hnk.aws.service.impl;

import com.hnk.aws.model.UserGroup;
import com.hnk.aws.repository.UserGroupRepository;
import com.hnk.aws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author hnguyen.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Override
    public List<UserGroup> listAllGroups() {
        return userGroupRepository.listAllGroups();
    }
}
