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

    @Test
    public void getsClosestShop() throws Exception {
        //Create 3 shops - Kharadi, Hinjewadi and Bengaluru
        Shop kharadiShop = new Shop().withShopName("kharadi-shop")
                .withAddress(new Address()
                        .withAddress("EON IT Park, Kharadi")
                        .withPostCode(411014)
                        .withLatitude(18.5515)
                        .withLongitude(73.9508));
        shopRepository.save(kharadiShop);

        Shop hinjewadiShop = new Shop().withShopName("hinjewadi-shop")
                .withAddress(new Address()
                        .withAddress("Hinjewadi Tech Park, Hinjewadi")
                        .withPostCode(411054)
                        .withLatitude(18.5917)
                        .withLongitude(73.6838));
        shopRepository.save(hinjewadiShop);

        Shop bengaluruShop = new Shop().withShopName("bengaluru-shop")
                .withAddress(new Address()
                        .withAddress("Electronic city, Bengaluru")
                        .withPostCode(560100)
                        .withLatitude(12.8399)
                        .withLongitude(77.6770));
        shopRepository.save(bengaluruShop);

        //Check which is closer to MG Road, Bengaluru
        Shop closestToMgRoad = shopService.findNearestShop(12.973801,77.611885);
        Assert.assertTrue("Wrong result, bengaluru should be closest to MG Road",closestToMgRoad.getShopName().equals("bengaluru-shop"));

        //Check which is closer to London
        Shop closestToLondon = shopService.findNearestShop(51.5074,-0.062553);
        Assert.assertTrue("Wrong result, hinjewadi is closest to London", closestToLondon.getShopName().equals("hinjewadi-shop"));

        //Check which is closest to Pune Airport
        Shop closestToPuneAirport = shopService.findNearestShop(18.5789,73.9091);
        Assert.assertTrue("Wrong result, kharadi is closest to pune airport", closestToPuneAirport.getShopName().equals("kharadi-shop"));
    }
}
