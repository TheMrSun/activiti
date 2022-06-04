import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * @author Slience
 * @version 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:activiti-spring.xml")
public class ActivitiSpringTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private DataSource dataSource;
    @Test
    public void test(){
        System.out.println(dataSource);
        System.out.println(repositoryService);
    }
}
