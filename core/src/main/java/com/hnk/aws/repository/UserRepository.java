package com.hnk.aws.repository;

import com.hnk.aws.dao.UserDao;
import com.hnk.aws.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hnguyen
 */
public interface UserRepository extends CrudRepository<User, Long>, UserDao {
}
