package com.hnk.aws.service;

import com.hnk.aws.model.UserGroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hnguyen.
 */
public interface UserService {
    public List<UserGroup> listAllGroups();
}
