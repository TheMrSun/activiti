
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 *  Activiti权威指南书配套代码
 *  
 * @author shareniu 分享牛 http://www.shareniu.com/
 *
 */
public class ProcessEnginesTest {


	@Autowired
	private ProcessEngine processEngine;

	@Test
	public void getDefaultProcessEngine() {
		ProcessEngine  pe = ProcessEngines.getDefaultProcessEngine();
		System.out.println(pe);
//		assertNotNull("not null", pe);

	}
	
}
