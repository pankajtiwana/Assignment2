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

import cpd4414.assign2.OrderQueue.NoTimeProcessedException;
import cpd4414.assign2.OrderQueue.TimeNullException;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Pankaj
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
        order.addPurchase(new Purchase(11, 3));
        order.addPurchase(new Purchase(12, 4));
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
        order.addPurchase(new Purchase(13, 5));
        order.addPurchase(new Purchase(14, 6));
        try{
        orderQueue.add(order);
        }
        catch(OrderQueue.NoCustomerException e )
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
        order.addPurchase(new Purchase(15, 6));
        
         orderQueue.add(order);
         Order order1= new Order("C06521132", "Singh");
         order1.addPurchase(new Purchase(13, 7));
       
        orderQueue.add(order1);
        Order result= orderQueue.next();
        assertEquals(result, order);
       assertNull(result.getTimeProcessed());
        
    }
           
    
     @Test
    public void testWhennoNextorderExist() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException{
        
        OrderQueue orderQueue= new OrderQueue();
        
        Order result= orderQueue.next();
        assertNull(result);
       
        
    }
    
    @Test 
    public void testWhenTimerecievedisSetthanSettimeProccessedToNow() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, TimeNullException {
        
     OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase(3, 8));
        
         orderQueue.add(order);
         Order order1= new Order("C06521132", "Singh");
         order1.addPurchase(new Purchase(2, 9));
       
        orderQueue.add(order1);
        Order nextOrder= orderQueue.next();
        orderQueue.process(nextOrder);
                long expResult = new Date().getTime();
        long result = order.getTimeProcessed().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);

        
    }
    
    @Test 
    public void testWhenTimerecievedisNull() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, OrderQueue.TimeNullException {
        boolean catchexception= false;
     OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase(23, 8));
        try{
          orderQueue.process(order);   
        }
        catch(TimeNullException te)
        {
            catchexception=true;
        }
       
              assertTrue(catchexception);

        
    }
    
    
    @Test 
    public void testWhentheOrderIsintactSettimeFullfilledtoNow() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, TimeNullException, NoTimeProcessedException {
        
     OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase(23, 8));
        
         orderQueue.add(order);
         Order order1= new Order("C06521132", "Singh");
         order1.addPurchase(new Purchase(234, 9));
       
        orderQueue.add(order1);
        Order nextOrder= orderQueue.next();
        orderQueue.process(nextOrder);
        
        orderQueue.fullfillOrder(nextOrder);
        
                long expResult = new Date().getTime();
        long result = order.getTimeFulfilled().getTime();
        assertTrue(Math.abs(result - expResult) < 1000);

        
    } 
    
    
    @Test 
    public void testWhenFulfillinfOrderandTimeReceivedisNull() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, OrderQueue.TimeNullException, NoTimeProcessedException {
        boolean catchexception= false;
     OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase(23, 8));
        try{
          orderQueue.fullfillOrder(order);   
        }
        catch(TimeNullException te)
        {
            catchexception=true;
        }
       
              assertTrue(catchexception);

        
    }
    
    @Test 
    public void testWhenFulfillinfOrderandTimeProcessedisNull() throws OrderQueue.NoCustomerException, OrderQueue.NoPurchasesException, OrderQueue.TimeNullException {
        boolean catchexception= false;
     OrderQueue orderQueue= new OrderQueue();
        Order order= new Order("C0652113", "Pankaj");
        order.addPurchase(new Purchase(23, 8));
        orderQueue.add(order);
        try{
          orderQueue.fullfillOrder(order);  
        }
        catch(NoTimeProcessedException te)
        {
            catchexception=true;
        }
       
              assertTrue(catchexception);

        
    }


}
    

