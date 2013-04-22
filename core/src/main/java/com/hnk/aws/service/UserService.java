//
// @Copyright 2013.
//
package com.hnk.aws.service;

import com.hnk.aws.model.Account;
import com.hnk.aws.model.User;
import com.hnk.aws.model.UserGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Service.
 *
 * @author hnguyen.
 */
@Transactional
public interface UserService {
    /**
     * Createa a new user account.
     *
     * @param user        the {@code User}.
     * @param accountType the account type.
     * @return a newly created {@code Account}.
     */
    public Account createUserAccount(User user, Account.AccountType accountType);

    /**
     * List all groups.
     *
     * @return a list of groups.
     */
    public List<UserGroup> listAllGroups();

    /**
     * Save user with username and password.
     *
     * @param userName the email address.
     * @param password the user password.
     * @return the saved user.
     */
    public User save(String userName, String password);

    /**
     * List all users.
     *
     * @return a list of users.
     */
    public List<User> list();

    /**
     * Find by username
     */
    public User findByUsername(String username);

    /**
     * List all accounts.
     *
     * @return a list of db accounts.
     */
    public List<Account> listAccounts();
}
