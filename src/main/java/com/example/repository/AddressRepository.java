package com.example.repository;

import com.example.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long> {
	
	List<Address> findByCity(String city);

	@Query("select a from Address a where a.zipcode = ?1")
	Address findByZipcode(Long zipcode);

}
