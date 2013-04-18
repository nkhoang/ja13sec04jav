package com.hnk.aws.service;

import com.hnk.aws.model.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hnguyen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dao.xml",
        "classpath*:applicationContext-service.xml"})
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
}
