package com.innovationlabs.api.dao;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by cv on 4/5/17.
 */
@Embeddable
@Data
public class Address {
    private String address;
    private Integer postCode;
    private Double latitude;
    private Double longitude;

    public Address withAddress(String address) {
        this.address = address;
        return this;
    }

    public Address withPostCode(Integer postCode) {
        this.postCode = postCode;
        return this;
    }

    public Address withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Address withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
}
