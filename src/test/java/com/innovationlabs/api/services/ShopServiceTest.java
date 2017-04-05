package com.innovationlabs.api.services;

import com.innovationlabs.api.dao.Address;
import com.innovationlabs.api.dao.Shop;
import com.innovationlabs.api.repositories.ShopRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by cv on 4/5/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeServiceTest.class);

    @Autowired
    ShopService shopService;

    @Autowired
    ShopRepository shopRepository;

    @After
    public void cleanUp() {
        logger.info("Cleaning up");
        shopRepository.deleteAll();
    }

    @Test
    public void insertsShop() throws Exception {
        Shop s1 = new Shop().withAddress(new Address()
                .withAddress("EON IT Park, Pune")
                .withPostCode(411014))
                .withShopName("s1");
        Shop shop = shopService.insertShop(s1);
        Assert.assertEquals(shop.getShopName(), s1.getShopName());
        Assert.assertEquals(shop.getAddress(), s1.getAddress());
    }

    @Test
    public void updatesShop() throws Exception {
        Shop originalShop = new Shop().withAddress(new Address()
                .withAddress("EON IT Park, Pune")
                .withPostCode(411014))
                .withShopName("s1");
        Shop savedShop = shopService.insertShop(originalShop);
        Assert.assertEquals(savedShop.getShopName(), originalShop.getShopName());
        Assert.assertEquals(savedShop.getAddress(), originalShop.getAddress());

        Shop updatedShop = new Shop().withAddress(new Address()
                .withAddress("Koregaon Park , Pune")
                .withPostCode(411001))
                .withShopName("s1");

        savedShop = shopService.insertShop(updatedShop);
        Assert.assertEquals(savedShop.getShopName(), originalShop.getShopName());
        Assert.assertEquals(savedShop.getAddress(), originalShop.getAddress());

        savedShop = shopRepository.findOne("s1");
        Assert.assertEquals(savedShop.getAddress(), updatedShop.getAddress());
        Assert.assertEquals(savedShop.getShopName(), updatedShop.getShopName());
    }

    @Test
    public void exceptionOnSimultaneousInsertions() throws Exception {
        shopRepository.deleteAll();
        Shop s1 = new Shop().withAddress(new Address()
                .withAddress("EON IT Park, Pune")
                .withPostCode(411014))
                .withShopName("s1");

        Thread t = new Thread(() -> {
            try {
                shopService.insertShop(s1);
            } catch (Exception e) {
                logger.error("Exception occurred ", e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                shopService.insertShop(s1);
            } catch (Exception e) {
                logger.error("Exception occurred ", e);
            }
        });

        t.start();
        t2.start();
        t.join();
        t2.join();
    }
}
