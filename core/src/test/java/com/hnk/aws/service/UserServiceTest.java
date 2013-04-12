package com.hnk.aws.service;

import com.hnk.aws.model.User;
import com.hnk.aws.model.UserLog;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author hnguyen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service" +
        ".xml"})
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceTest.class.getCanonicalName());

    @Test
    public void testSaveUser() {
        User savedUser = userService.save("nkhoang.it", "password");
        Assert.assertNotNull(savedUser);
    }

    @Test
    public void testSaveUsers() {
        try {
            userService.saveUsers();
        } catch (Exception ex) {
            // check the result;
            List<User> result = userService.list();
            List<UserLog> userLogs = userLogService.list();
            LOG.debug("Total users: " + result.size());
            LOG.debug("Total user logs: " + userLogs.size());
            Assert.assertNotNull(result);
            Assert.assertEquals(1, result.size());
        }
    }
}
