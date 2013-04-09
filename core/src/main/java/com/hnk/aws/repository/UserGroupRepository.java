package com.hnk.aws.repository;

import com.hnk.aws.model.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author hnguyen
 */
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    public List<UserGroup> findByName(String name);

    @Query("select g from UserGroup g")
    public List<UserGroup> listAllGroups();
}
