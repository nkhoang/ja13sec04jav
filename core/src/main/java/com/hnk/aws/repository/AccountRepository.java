package com.hnk.aws.repository;

import com.hnk.aws.model.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hnguyen
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
}
