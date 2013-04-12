package com.hnk.aws.repository;

import com.hnk.aws.model.UserLog;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hnguyen
 */
public interface UserLogRepository extends CrudRepository<UserLog, Long> {
}
