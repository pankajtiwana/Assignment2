/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cpd4414.assign2;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueueTest {
    
    public OrderQueueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testWhenCustomerExistsAndPurchasesExistThenTimeReceivedIsNow() throws Exception {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("CUST00001", "ABC Cafeteria");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        orderQueue.add(order);
        
        long expResult = new Date().getTime();
        long result = order.getTimeReceived().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);
    }
    @Test
    public void testWhenNoCustomerException() throws OrderQueue.NoPurchasesException
    {
        boolean catchexception= false;
         OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("", "");
        order.addPurchase(new Purchase("PROD0004", 450));
        order.addPurchase(new Purchase("PROD0006", 250));
        try{
        orderQueue.add(order);
        }
        catch(OrderQueue.NoCustomerException e)
        {
            catchexception=true;
        }
        assertTrue(catchexception);
    }
    
    
    @Test
    public void testWhenNoPurchases() throws OrderQueue.NoCustomerException
    {
         boolean catchexception= false;
         OrderQueue orderQueue = new OrderQueue();
        Order order = new Order("bbh","njhbhj");
        
       
        try{
        orderQueue.add(order);
        }
        catch(OrderQueue.NoPurchasesException e)
        {
            catchexception=true;
        }
        assertTrue(catchexception);
    }
    
    @Test
    public void testWhenNextorderExist() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException{
        
        OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase("PROD0004", 450));
        
         orderQueue.add(order);
         Order order1= new Order("C06521132", "Singh");
         order1.addPurchase(new Purchase("PROD00034", 4530));
       
        orderQueue.add(order1);
        Order result= orderQueue.next();
        assertEquals(result, order);
        assertNull(result.getTimeReceived());
        
    }
           
}
    

