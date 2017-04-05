package com.innovationlabs.api.domain;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
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
