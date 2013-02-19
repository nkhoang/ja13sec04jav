package com.hnk.aws.dao;

import com.hnk.aws.model.User;
import com.hnk.aws.model.validator.group.UserRegistrationCheck;
import com.hnk.aws.repository.UserRepository;
import com.hnk.aws.util.ValidationUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import javax.validation.Validator;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service.xml"})
public class UserDaoTest extends BaseDaoTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserDaoTest.class.getCanonicalName());
    @Autowired
    private Validator validator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        User u = new User();
        u.setBirthDate(new Date());
        u.setUsername("nkhoang.it");
        u.setPassword(passwordEncoder.encodePassword("password", null));
        User savedUser = userRepository.save(u);
        Assert.assertNotNull(savedUser);
    }

    @Test
    public void testLoadUserByName() {
        User u = (User) userRepository.loadUserByUsername("user01");
        Assert.assertNotNull(u);
        Assert.assertEquals("user01@user.com", u.getEmail());
    }


    @Test
    public void testValidateUser() {
        User u1 = new User();
        u1.setFirstName("1");
        u1.setLastName("2");
        u1.setPassword("123");
        try {
            ValidationUtils.checkConstraintViolations(validator.validate(u1, UserRegistrationCheck.class));
            Assert.fail("Should throw exception here.");
        } catch (Exception e) {
            LOG.info(String.format("Validation exception message: %s", e.getMessage()));
            // do nothing.
        }
    }
}
