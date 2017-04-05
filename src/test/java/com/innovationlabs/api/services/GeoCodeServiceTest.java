package com.innovationlabs.api.services;

import com.innovationlabs.api.dao.Geocode;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by cv on 4/5/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoCodeServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(GeoCodeServiceTest.class);

    @Autowired
    GeoCodeService geoCodeService;

    @Test
    public void addressToLatLong() throws Exception {
        Geocode geocode = geoCodeService.addressToLatLong("EON IT Park, Pune");
        Assert.assertThat("Latitude mismatch", geocode.getLatitude(), Matchers.allOf(Matchers.greaterThan(18.5), Matchers.lessThan(18.6)));
        Assert.assertThat("Longitude mismatch", geocode.getLongitude(), Matchers.allOf(Matchers.greaterThan(73.9), Matchers.lessThan(74.0)));
    }

    @Test
    public void addressToLatLongInvalid() throws Exception {
        Geocode geocode = geoCodeService.addressToLatLong("assdfferwgfw");
        Assert.assertThat("Latitude mismatch", geocode, Matchers.is((Geocode) null));
    }

    @Test
    public void distanceBetweenGeoCodes() throws Exception {
        Geocode geocode1 = new Geocode().withLat(18.55).withLng(73.95);
        Geocode geocode2 = new Geocode().withLat(18.0).withLng(74.0);
        Double distance = geoCodeService.distanceBetweenGeoCodes(geocode1, geocode2);
        Assert.assertThat("Distance mismatch", distance, Matchers.allOf(Matchers.greaterThan(61.0), Matchers.lessThan(62.0)));
    }

}