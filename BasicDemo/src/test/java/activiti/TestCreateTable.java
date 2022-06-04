package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * @author Slience
 * @version 1.0
 */
public class TestCreateTable {

    @Test
    public void testCreateTable() throws Exception {
        //配置activiti.cfg.xml 后activiti 自动创建需要表
        ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
        //可以自定义activiti.cfg.xml ,和 StandaloneInMemProcessEngineConfiguration bean 的id
        System.out.println(pe);
    }
}
