package com.innovationlabs.api.dao;

import lombok.Data;
import org.springframework.data.geo.Point;

import javax.persistence.Embeddable;

/**
 * Created by cv on 4/5/17.
 */
@Embeddable
@Data
public class Address {
    private String address;
    private Integer postCode;
    private Point location;
}
