package com.store;


import com.store.entity.Order;
import com.store.serveice.ex.ServiceException;
import com.store.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StoreApplication.class})
public class OrderServiceTests {
    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        try {
            Integer aid = 21;
            Integer[] cids = {4, 5, 6, 7};
            Integer uid = 31;
            String username = "订单管理员";
            Order order = orderService.create(aid, cids, uid, username);
            System.out.println(order);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
