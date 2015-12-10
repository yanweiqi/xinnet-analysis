package com.xinnet.xa.analyzer;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.beanutils.BeanUtils;

import com.xinnet.xa.analyzer.vo.OrderDetail;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) throws Exception {
		 OrderDetail od1 = new OrderDetail();
		 od1.setAgentCode("111");
		 od1.setBuyType("33");
		 OrderDetail od2 = new OrderDetail();
		 od2.setGoodsClassName("44");
		 od2.setOperationTime("66");
		 Map<String,String> map = BeanUtils.describe(od1);
		 Set<Entry<String,String>> set = map.entrySet();
		 for(Entry<String,String> entry:set){
			 System.out.println(entry.getKey()+":"+entry.getValue());
		 }
		 
		 
	}
}
