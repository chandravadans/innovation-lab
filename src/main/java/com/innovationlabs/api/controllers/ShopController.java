package com.innovationlabs.api.controllers;

import com.innovationlabs.api.dao.Shop;
import com.innovationlabs.api.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cv on 4/5/17.
 */
@RestController
public class ShopController {
    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public Page<Shop> findAllShops(Pageable pageable) throws Exception {
        return shopService.findAllShops(pageable);
    }

    @RequestMapping(value = "/shops/{shopName}", method = RequestMethod.GET)
    public Shop findShopByName(@PathVariable String shopName) throws Exception {
        return shopService.findByShopName(shopName);
    }

    @RequestMapping(value = "/shops", method = RequestMethod.POST)
    public Shop insertShop(@RequestBody Shop shop) throws Exception {
        return shopService.insertShop(shop);
    }
}
