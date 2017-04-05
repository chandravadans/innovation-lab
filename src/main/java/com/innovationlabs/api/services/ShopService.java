package com.innovationlabs.api.services;

import com.innovationlabs.api.dao.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by cv on 4/5/17.
 */
public interface ShopService {

    public Shop insertShop(Shop s) throws Exception;

    public Shop findByShopName(String name) throws Exception;

    public Page<Shop> findAllShops(Pageable pageable) throws Exception;

    public Page<Shop> findNearestShops(Double latitude, Double longitude, Pageable pageable) throws Exception;
}
