package com.hnk.aws.repository;

import com.hnk.aws.dao.UserDao;
import com.hnk.aws.model.User;
import com.hnk.aws.model.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author hnguyen
 */
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {
    public List<UserGroup> findByName(String name);
}
