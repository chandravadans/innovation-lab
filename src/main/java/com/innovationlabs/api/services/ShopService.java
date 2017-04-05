package com.innovationlabs.api.services;

import com.innovationlabs.api.dao.Shop;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by cv on 4/5/17.
 */
public interface ShopService {

    public Shop insertShop(Shop s) throws DataAccessException;

    public Shop findByShopName(String name) throws DataAccessException;

    public Page<Shop> findAllShops(Pageable pageable) throws DataAccessException;

    public Page<Shop> findNearestShops(Pageable pageable, Double latitude, Double longitude) throws DataAccessException;
}
