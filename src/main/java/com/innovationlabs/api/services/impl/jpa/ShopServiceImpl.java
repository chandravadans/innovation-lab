package com.innovationlabs.api.services.impl.jpa;

import com.innovationlabs.api.dao.Address;
import com.innovationlabs.api.dao.Geocode;
import com.innovationlabs.api.dao.Shop;
import com.innovationlabs.api.repositories.ShopRepository;
import com.innovationlabs.api.services.GeoCodeService;
import com.innovationlabs.api.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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
    public Page<Shop> findAllShops(Pageable pageable) throws Exception {
        return shopRepository.findAll(pageable);
    }

    @Override
    public Shop findNearestShop(Double latitude, Double longitude) throws Exception {
        Double latitudeDelta = 1.0;
        Double longitudeDelta = 1.0;
        //We need to wrap at latitude delta at 90 as that's the max value a latitude can take and
        //longitude delta is wrapped at 180 for the same reason.
        List<Shop> shortList = shopRepository.findShopsBetweenLatLong(
                (latitude - latitudeDelta) % 90,
                (latitude + latitudeDelta) % 90,
                (longitude - longitudeDelta) % 180,
                (longitude + longitudeDelta) % 180);
        while (shortList.size() == 0 && (latitudeDelta <= 45 || longitudeDelta <= 180)) {
            logger.debug("No shops found with latitude delta = " + latitudeDelta + " and longitude delta = " + longitudeDelta);
            latitudeDelta = latitudeDelta * 2;
            longitudeDelta = longitudeDelta * 2;
            shortList = shopRepository.findShopsBetweenLatLong(
                    (latitude - latitudeDelta) % 90,
                    (latitude + latitudeDelta) % 90,
                    (longitude - longitudeDelta) % 180,
                    (longitude + longitudeDelta) % 180);
        }
        Shop nearest = null;
        Double nearestDist = Double.MAX_VALUE;
        Geocode queriedGeocode = new Geocode().withLat(latitude).withLng(longitude);
        for (Shop shop : shortList) {
            Geocode thisGeocode = new Geocode().withLat(shop.getAddress().getLatitude()).withLng(shop.getAddress().getLongitude());
            Double dist = geoCodeService.distanceBetweenGeoCodes(queriedGeocode, thisGeocode);
            if (dist < nearestDist) {
                logger.debug("Found closer shop " + shop + " at a dist of " + dist);
                nearest = shop;
                nearestDist = dist;
            }
        }
        logger.info("Nearest shop is " + nearest + " at a distance of " + nearestDist);
        return nearest;
    }
}
