package com.hnk.aws.dao;

import com.hnk.aws.model.UserLog;
import com.hnk.aws.repository.UserLogRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service" +
        ".xml"})
public class UserLogRepositoryTest {
    @Autowired
    private UserLogRepository repository;
    @Test
    public void testSave() {
        UserLog userLog = new UserLog();
        userLog.setUserId(1L);
        userLog.setCreatedDate(new Date());

        repository.save(userLog);
    }

    @Test
    public void testGet() {
        UserLog log = repository.findOne(1L);
        Assert.assertNotNull(log);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testException() {
        repository.delete(10L);
    }

}
