package com.innovationlabs.api.domain;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by cv on 4/5/17.
 */
@Embeddable
@Data
public class Address {
    String address;
    Integer postCode;
    Double latitude;
    Double longitude;

}
