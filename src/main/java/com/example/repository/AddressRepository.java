package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Address;

public interface AddressRepository extends CrudRepository<Address,Long> {

}
