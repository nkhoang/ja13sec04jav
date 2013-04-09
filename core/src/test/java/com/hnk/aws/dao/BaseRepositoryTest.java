package com.hnk.aws.dao;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Statement;

/**
 * @author hnguyen
 */

public class BaseRepositoryTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseRepositoryTest.class.getCanonicalName());

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
