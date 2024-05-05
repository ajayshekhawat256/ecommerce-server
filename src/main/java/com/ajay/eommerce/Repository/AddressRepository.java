package com.ajay.eommerce.Repository;

import com.ajay.eommerce.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
