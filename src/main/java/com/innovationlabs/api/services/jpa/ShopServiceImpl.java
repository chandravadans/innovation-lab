package com.innovationlabs.api.services.jpa;

import com.innovationlabs.api.domain.Shop;
import com.innovationlabs.api.repositories.ShopRepository;
import com.innovationlabs.api.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * Created by cv on 4/5/17.
 */
@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop insertShop(Shop s) throws DataAccessException {
        return null;
    }

    @Override
    public Shop findByShopName(String name) throws DataAccessException {
        return null;
    }

    @Override
    public Page<Shop> findAllShops(Pageable pageable) throws DataAccessException {
        return null;
    }

    @Override
    public Page<Shop> findNearestShops(Pageable pageable, Double latitude, Double longitude) throws DataAccessException {
        Point point = new Point(latitude, longitude);
        return null;
    }
}
