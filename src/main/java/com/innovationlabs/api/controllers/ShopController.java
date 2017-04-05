package com.innovationlabs.api.controllers;

import com.innovationlabs.api.dao.Shop;
import com.innovationlabs.api.services.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cv on 4/5/17.
 */
@RestController
@RequestMapping("/v1")
@Api(basePath = "/shops", value = "Shops", description = "Operations on retail shops", produces = "application/json")
public class ShopController {
    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    @ApiOperation(value = "Find all shops", notes = "Lists all retail shops in the repository")
    public Page<Shop> findAllShops(Pageable pageable) throws Exception {
        return shopService.findAllShops(pageable);
    }

    @RequestMapping(value = "/shops/{shopName}", method = RequestMethod.GET)
    @ApiOperation(value = "Find a specific shop", notes = "Fetches a shop with a specific name from the repository")
    public Shop findShopByName(@PathVariable String shopName) throws Exception {
        return shopService.findByShopName(shopName);
    }

    @RequestMapping(value = "/shops/nearest", method = RequestMethod.GET)
    @ApiOperation(value = "Find the nearest shop", notes = "Given a latitude and longitude, fetches the nearest shop. Returns null if no shops")
    public Shop findShopByName(@RequestParam Double lat, @RequestParam Double lng) throws Exception {
        return shopService.findNearestShop(lat, lng);
    }

    @RequestMapping(value = "/shops", method = RequestMethod.POST)
    @ApiOperation(value = "Inserts or updates a specific shop", notes = "Inserts a shop if its not present in the repository, and updates it if already present")
    public Shop insertShop(@RequestBody Shop shop) throws Exception {
        return shopService.insertShop(shop);
    }
}
