package com.innovationlabs.api.services;

import com.innovationlabs.api.dao.Geocode;

/**
 * Created by cv on 4/5/17.
 */
public interface GeoCodeService {
    public Geocode addressToLatLong(String address);

    public Double distanceBetweenGeoCodes(Geocode geocode1, Geocode geocode2);
}
