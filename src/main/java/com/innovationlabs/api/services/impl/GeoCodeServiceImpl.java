package com.innovationlabs.api.services.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.innovationlabs.api.dao.Geocode;
import com.innovationlabs.api.services.GeoCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by cv on 4/5/17.
 */
@Service("geocodeService")
public class GeoCodeServiceImpl implements GeoCodeService {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeServiceImpl.class);

    private GeoApiContext context;

    @Value("${innovationlabs.api.geocoding.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        context = new GeoApiContext().setApiKey(apiKey);
        logger.debug("Instantiated Google Geocode API client successfully!");
    }

    @Override
    public Geocode addressToLatLong(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.newRequest(context)
                    .address(address)
                    .await();
            if (results.length > 0) {
                Double latitude = results[0].geometry.location.lat;
                Double longitude = results[0].geometry.location.lng;
                return new Geocode().withLat(latitude).withLng(longitude);
            } else {
                return null;
            }
        } catch (ApiException | InterruptedException | IOException ex) {
            logger.error("Error while converting address to lat long using Google API ", ex);
            return null;
        }
    }

    @Override
    /**
     * This is the implementation Haversine Distance Algorithm between two places
     * @author cv
     *  R = earth’s radius (mean radius = 6,371km)
    Δlat = lat2− lat1
    Δlong = long2− long1
    a = sin²(Δlat/2) + cos(lat1).cos(lat2).sin²(Δlong/2)
    c = 2.atan2(√a, √(1−a))
    d = R.c
     *
     */
    public Double distanceBetweenGeoCodes(Geocode geocode1, Geocode geocode2) {
        final int R = 6371; // Radius of the earth in km
        Double lat1 = geocode1.getLatitude();
        Double lon1 = geocode1.getLongitude();
        Double lat2 = geocode2.getLatitude();
        Double lon2 = geocode2.getLongitude();
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
