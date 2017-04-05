package com.innovationlabs.api.services;

import com.innovationlabs.api.domain.Shop;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by cv on 4/5/17.
 */
public interface ShopService {
    public Shop insertShop(Shop s) throws DataAccessException;

    public Page<Shop> findAllShops(Pageable pageable) throws DataAccessException;

    public Page<Shop> findNearestShops(Pageable pageable) throws DataAccessException;
}
