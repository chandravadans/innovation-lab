package com.innovationlabs.api.jpa;

import com.innovationlabs.api.dao.Address;
import com.innovationlabs.api.dao.Geocode;
import com.innovationlabs.api.dao.Shop;
import com.innovationlabs.api.repositories.ShopRepository;
import com.innovationlabs.api.services.GeoCodeService;
import com.innovationlabs.api.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cv on 4/5/17.
 */
@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

    private ShopRepository shopRepository;
    private GeoCodeService geoCodeService;
    private Set<String> activeCache;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, GeoCodeService geoCodeService) {
        this.shopRepository = shopRepository;
        this.geoCodeService = geoCodeService;
        activeCache = new HashSet<>();
    }

    @Override
    public Shop insertShop(Shop s) throws Exception {
        try {

            if (activeCache.contains(s.getShopName())) {
                throw new Exception("Resource is being modified by another user, please try again");
            } else {
                activeCache.add(s.getShopName());
            }

            enrichDetailsForShop(s);

            //Check if there's a shop already existing
            Shop existingShop = shopRepository.findOne(s.getShopName());
            Shop savedShop;
            if (existingShop == null) {
                logger.debug("No existing shop with given details exists");
                savedShop = shopRepository.save(s);
                logger.debug("Saved shop successfully!");
            } else {
                logger.debug("Found an existing shop with given details. Updating it");
                savedShop = existingShop;
                shopRepository.save(s);
                logger.debug("Updated shop successfully!");
            }
            activeCache.remove(s.getShopName());
            return savedShop;
        } catch (Exception e) {
            logger.error("Exception while saving shop", e);
            throw e;
        }
    }

    void enrichDetailsForShop(Shop s) throws Exception {
        //Enrich shop with lat long details
        try {
            logger.debug("Fetching latlong details for shop");
            Geocode geocode = geoCodeService.addressToLatLong(s.getAddress().getAddress() + " " + s.getAddress().getPostCode());
            if (geocode == null) {
                logger.error("Couldn't fetch geocode details for shop " + s);
                activeCache.remove(s.getShopName());
                throw new Exception("Invalid address, please try a valid address");
            }

            s.setAddress(new Address()
                    .withAddress(s.getAddress().getAddress())
                    .withLatitude(geocode.getLatitude())
                    .withLongitude(geocode.getLongitude())
                    .withPostCode(s.getAddress().getPostCode()));
        } catch (Exception e) {
            logger.error("Error while enriching shop", e);
            throw e;
        }
    }

    @Override
    public Shop findByShopName(String name) throws Exception {
        return shopRepository.findOne(name);
    }

    @Override
    public Page<Shop> findAllShops(Pageable pageable) throws DataAccessException {
        return shopRepository.findAll(pageable);
    }

    @Override
    public Page<Shop> findNearestShops(Double latitude, Double longitude, Pageable pageable) throws DataAccessException {

        return null;
    }
}
