package com.innovationlabs.api.repositories;

import com.innovationlabs.api.dao.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cv on 4/5/17.
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    @Query("select s from shops s " +
            "where s.address.latitude between ?1 and ?2 and s.address.longitude between ?3 and ?4")
    public List<Shop> findShopsBetweenLatLong(Double latitudeFrom, Double latitudeTo, Double longitudeFrom, Double longitudeTo);

}
