package com.example.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.Address;
import com.example.model.Results;
import com.example.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository repository;
	
	@Value("${url.google}")
	private String urlGoogle;
	
	@Value("${google.api.key}")
	private String apiKey;
	
	private RestTemplate restTemplate;

	public Address update(Address address) {
		if (address.getId() == null) {
			throw new IllegalArgumentException("Id deve ser informado");
		}

		Address addressRet = this.repository.save(address);

		return addressRet;
	}

	public void remove(Long id) {

		Optional<Address> address = this.repository.findById(id);

		this.repository.delete(address.get());
	}
	
	public Results getLatitudeLongitudeResults(String address) {
		
		address = address.replace(" ", "+");

		StringBuilder url = new StringBuilder(urlGoogle);
		url.append(address);
		url.append("&key="+this.apiKey);

		restTemplate = new RestTemplate();
		ResponseEntity<Results> results = restTemplate.getForEntity(url.toString(), Results.class);
		
		return results.getBody();

	}
}
