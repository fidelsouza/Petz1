package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.Address;
import com.example.repository.AddressRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Petz1ApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private AddressRepository repository;

	@LocalServerPort
	private int port;

	@Test
	void searchByCity() {

		Address add = new Address(1L, "Rua3", 3L, "Vizinhanca legal", "Goiania", "Goias", "Brasil");
		List lista = new ArrayList<>();
		lista.add(add);

		String city = "Goiania";

		BDDMockito.when(repository.findByCity(city)).thenReturn(lista);

		String url = "http://localhost:" + port + "/address/find-city";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("city", city);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<Address[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				Address[].class);

		assertThat(response.getBody()).isNotNull();

	}

	@Test
	void deleteAddress() {

		Long id = 1L;

		Address add = new Address(1L, "Rua3", 3L, "Vizinhanca legal", "Goiania", "Goias", "Brasil");

		Optional<Address> address = Optional.of(add);

		BDDMockito.when(repository.findById(id)).thenReturn(address);

		String url = "http://localhost:" + port + "/address/remove";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", id);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, entity,
				String.class);

		assertThat(response.getBody()).isNotEmpty();
	}

	@Test
	void saveAddress() {

		Address add = new Address(1L, "Rua3", 3L, "Vizinhanca legal", "Goiania", "Goias", "Brasil");

		BDDMockito.when(repository.save(add)).thenReturn(add);

		String url = "http://localhost:" + port + "/address/insert";

		restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Address> requestEntity = new HttpEntity<Address>(add, headers);
		HttpEntity<Address> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Address.class);

		assertThat(response).isNotNull();

	}

	@Test
	void updateAdress() {

		Address add = new Address(1L, "Rua3", 3L, "Vizinhanca legal", "Goiania", "Goias", "Brasil");
	
		BDDMockito.when(repository.save(add)).thenReturn(add);

		String url = "http://localhost:" + port + "/address/update";

		//restTemplate.getRestTemplate().getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Address> requestEntity = new HttpEntity<Address>(add, headers);
		HttpEntity<Address> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Address.class);

		assertThat(response).isNotNull();

	}

}
