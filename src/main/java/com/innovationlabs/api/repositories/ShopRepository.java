package com.innovationlabs.api.repositories;

import com.innovationlabs.api.dao.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cv on 4/5/17.
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
}
