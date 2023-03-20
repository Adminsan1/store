package com.store;

import com.store.serveice.ICartService;
import com.store.serveice.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StoreApplication.class})
public class CartServiceTests {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        //cartService.addToCart(2, 2, 1, "Admin");
        try {
            Integer uid = 2;
            Integer pid = 10000003;
            Integer amount = 1;
            String username = "Tom";
            cartService.addToCart(uid, pid, amount, username);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
