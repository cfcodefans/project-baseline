package cw.project.x1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SettingTests {
    private static Logger log = LoggerFactory.getLogger(SettingTests.class);

    @Autowired
    DataSource ds;

    @Test
    public void testDataSource() {
        Assert.assertNotNull(ds);
    }
}
