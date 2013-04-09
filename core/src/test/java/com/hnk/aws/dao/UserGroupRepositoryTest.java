package com.hnk.aws.dao;

import com.hnk.aws.model.UserGroup;
import com.hnk.aws.model.UserRole;
import com.hnk.aws.repository.UserGroupRepository;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-dao.xml", "classpath*:applicationContext-service" +
        ".xml"})
public class UserGroupRepositoryTest {
    @Autowired
    private UserGroupRepository userGroupRepository;

    @Test
    @Transactional(value = "mainTx")
    public void testSaveUserGroup() {
        UserRole role = new UserRole();
        role.setName("ROLE_USER");

        UserGroup ug = new UserGroup();
        ug.setName("ADMIN");
        ug.getRoles().addAll(Arrays.asList(role));
        userGroupRepository.save(ug);
        // test saved file
        List<UserGroup> groups = userGroupRepository.findByName("ADMIN");
        Assert.assertNotNull(groups);
        Assert.assertEquals(1, groups.size());
    }

    @Test
    public void testGetAllGroups() {
        Assert.assertNotNull(userGroupRepository.listAllGroups());
        Assert.assertEquals(1, userGroupRepository.listAllGroups().size());
    }

}
