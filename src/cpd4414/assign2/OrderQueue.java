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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueue {
    Queue<Order> orderQueue = new ArrayDeque<>();
    List<Order> orderList= new ArrayList<>();
    public void add(Order order) throws NoCustomerException, NoPurchasesException {
        if(order.getCustomerId().isEmpty() && order.getCustomerName().isEmpty())
        {
            throw new NoCustomerException("");
        }
        if(order.getListOfPurchases().isEmpty())
            
        {
            throw new NoPurchasesException("");
        }
        orderQueue.add(order);
        order.setTimeReceived(new Date());
    }

    public Order next() {
        return orderQueue.peek();//To change body of generated methods, choose Tools | Templates.
    }

    void process(Order nextOrder) throws TimeNullException {
        if(nextOrder.equals(next()))
        {
            boolean done=true;
            if(done)
            {
                for(Purchase p: nextOrder.getListOfPurchases())
                {
                    if(Inventory.getQuantityForId(p.getProductId()) < p.getQuantity())
                    {
                        done=false;
                    }
                }
            orderList.add(orderQueue.remove());
            nextOrder.setTimeProcessed(new Date());
            }
            
        } 
        
        else
        {

         throw new TimeNullException("no time recieved"); 
        }//To change body of generated methods, choose Tools | Templates.
    }

    void fullfillOrder(Order nextOrder) throws TimeNullException, NoTimeProcessedException {
        if(nextOrder.getTimeReceived()==null)
        {
            throw new TimeNullException("NO GIVEN TIME");
        }
        if(nextOrder.getTimeProcessed() == null)
        {
            throw new NoTimeProcessedException("no time processed");
        }
        if(orderList.contains(nextOrder))
        {
            nextOrder.setTimeFulfilled(new Date());
        }
    }

    String report() {
        String output = "";
        if (!(orderQueue.isEmpty() && orderList.isEmpty())) {
            JSONObject obj = new JSONObject();
            JSONArray orders = new JSONArray();
            for (Order o : orderList) {
                orders.add(o.toJSON());
            }
            for (Order o : orderQueue) {
                orders.add(o.toJSON());
            }
            obj.put("orders", orders);
            output = obj.toJSONString();
        }
        return output;
    }


   

   
    public class NoCustomerException extends Exception {
    
        public NoCustomerException(String msg) {
        
            super(msg);
        }
    }
    
    public class NoPurchasesException extends Exception {
    
        public NoPurchasesException(String msg) {
        
            super(msg);
        }
    }
    
    public class TimeNullException extends Exception {
        public TimeNullException(String msg) {
            super(msg);
        }
    }
    
      public class NoTimeProcessedException extends Exception {
        public NoTimeProcessedException(String msg) {
            super(msg);
        }
    }
}
