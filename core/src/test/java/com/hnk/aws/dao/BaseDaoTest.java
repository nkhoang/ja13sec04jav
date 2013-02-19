package com.hnk.aws.dao;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Statement;

/**
 * @author hnguyen
 */

public class BaseDaoTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDaoTest.class.getCanonicalName());

    /**
     * Execute SQL scripts in file specified by {@code sqlPath}.
     *
     * @param sqlPath the path to the sql file.
     * @throws Exception
     */
    private void executeSQL(DataSource ds, String sqlPath) throws Exception {
        Statement stmt = ds.getConnection().createStatement();
        for (String sql : StringUtils.split(FileUtils.readFileToString(FileUtils.toFile(this.getClass()
                .getClassLoader().getResource(sqlPath))), ";")) {
            stmt.execute(sql.trim());
        }
    }
}
