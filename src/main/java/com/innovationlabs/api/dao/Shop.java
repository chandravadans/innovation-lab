package com.innovationlabs.api.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by cv on 4/5/17.
 */
@Entity(name = "shops")
@Data
public class Shop {

    @Id
    @Column(unique = true)
    private String shopName;

    private Address address;

    public Shop withShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public Shop withAddress(Address address) {
        this.address = address;
        return this;
    }
}
