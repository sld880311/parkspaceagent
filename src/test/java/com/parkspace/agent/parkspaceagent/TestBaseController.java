package com.parkspace.agent.parkspaceagent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Title: TestBaseController.java
 * @Package com.parkspace.controller
 * <p>Description:</p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月1日 上午10:38:14</p>
*/
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
//单元测试的时候真实的开启一个web服务  
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/dispatcher-servlet.xml"}) 
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用  
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)  
//@Transactional  
public class TestBaseController extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
    protected WebApplicationContext wac;
    
    protected MockMvc mvc;
    //该方法在每个方法执行之前都会执行一遍  
    @Before
    public void setUp() {
        this.mvc=MockMvcBuilders.webAppContextSetup(wac).build();
    }
    /**  
     * perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；  
     * get:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：
     * 		根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。  
     * param：添加request的参数，如上面发送请求的时候带上了了pcode = root的参数。
     * 		假如使用需要发送json数据格式的时将不能使用这种方式，可见后面被@ResponseBody注解参数的解决方法  
     * andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；  
     * andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；  
     * andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）  
     * @throws Exception  
     */ 
    @Test
    public void testDemo() {
    	
    }
}
