package com.innovationlabs.api.dao;

import lombok.Data;

/**
 * Created by cv on 4/5/17.
 */
@Data
public class Geocode {
    Double latitude;
    Double longitude;
    Integer postCode;

    public Geocode withLat(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Geocode withLng(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Geocode withPostCode(Integer postCode) {
        this.postCode = postCode;
        return this;
    }
}
