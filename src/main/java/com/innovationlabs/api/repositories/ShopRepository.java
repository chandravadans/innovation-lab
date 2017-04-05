package com.innovationlabs.api.repositories;

import com.innovationlabs.api.domain.Shop;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cv on 4/5/17.
 */
public interface ShopRepository extends JpaRepository<Shop, String> {
}
