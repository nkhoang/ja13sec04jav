package com.hnk.aws.service.impl;

import com.hnk.aws.model.Account;
import com.hnk.aws.model.User;
import com.hnk.aws.model.UserGroup;
import com.hnk.aws.model.UserLog;
import com.hnk.aws.repository.AccountRepository;
import com.hnk.aws.repository.UserGroupRepository;
import com.hnk.aws.repository.UserLogRepository;
import com.hnk.aws.repository.UserRepository;
import com.hnk.aws.service.UserService;
import org.jasypt.spring.security3.PasswordEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author hnguyen.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    public Account createUserAccount(User user, Account.AccountType accountType) {
        Account account = new Account();
        account.setAccountType(accountType);
        account.setUser(user);
        user.setAccount(account);

        userRepository.save(user);

        return account;
    }

    @Override
    public List<UserGroup> listAllGroups() {
        return userGroupRepository.listAllGroups();
    }

    @Override
    public User save(String userName, String password) {
        User u = new User();
        u.setBirthDate(new DateTime());
        u.setPassword(passwordEncoder.encodePassword(password, null));

        User savedU = userRepository.save(u);
        // save log
        UserLog log = new UserLog();
        log.setUserId(savedU.getId());
        log.setCreatedDate(new Date());

        userLogRepository.save(log);
        return savedU;
    }

    /**
     * {@inheritDoc}
     */
    public List<User> list() {
        List<User> users = new ArrayList<User>();
        Iterator<User> userIterator = userRepository.findAll().iterator();
        while (userIterator.hasNext()) {
            users.add(userIterator.next());
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);

    }

    public List<Account> listAccounts() {
        Iterator<Account> accountIterator = accountRepository.findAll().iterator();
        List<Account> accountList = new ArrayList<Account>();
        while (accountIterator.hasNext()) {
            accountList.add(accountIterator.next());
        }
        return accountList;
    }
}
