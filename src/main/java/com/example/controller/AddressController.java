package com.example.controller;

import com.example.model.Address;
import com.example.model.Results;
import com.example.repository.AddressRepository;
import com.example.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping(value = "/address")
@RestController()
public class AddressController {

	@Autowired
	AddressRepository repository;
	
	@Autowired
	AddressService service;

	private RestTemplate restTemplate;
	
	@GetMapping("/latitudeLongitudeFromGoogle")
	ResponseEntity<Results> latitudeAndLongitudeFromGoogle(@RequestParam( required = true) String address) {
		
		Results res = this.service.getLatitudeLongitudeResults(address);

		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/all")
	ResponseEntity<Iterable<Address>> findAll (){
		
		Iterable<Address> ret = repository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}

	@GetMapping("/find-by-zipcode")
	ResponseEntity<Address> findByZipcode (@RequestParam(required = true) Long zipcode){

		Address ret = repository.findByZipcode(zipcode);

		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@GetMapping("/find-city")
	ResponseEntity<List<Address>> findAll (@RequestParam(required = true) String city){
		
		List<Address> ret = repository.findByCity(city);
		
		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@PostMapping("/insert")
	ResponseEntity<Address> create(@RequestBody Address address){
		Address ret = repository.save(address);
		
		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Address> update(@RequestBody Address address) {
		
		Address addressRet = this.service.update(address);

		return ResponseEntity.status(HttpStatus.OK).body(addressRet);
	}

	@DeleteMapping("/remove")
	public ResponseEntity<String> remove(@RequestParam(required = true) Long id) {

		this.service.remove(id);

		return ResponseEntity.status(HttpStatus.OK).body("Removido");
	}

//	@GetMapping("/cliente")
//	ResponseEntity<Cliente> cliente(@RequestParam(value = "cpf", required = true) String cpf) {
//
//		Cliente cliente = cadastroService.findOneByCpf(cpf); 
//
//		return ResponseEntity.status(HttpStatus.OK).body(cliente);
//	}
//
//	@GetMapping("/endereco")
//	ResponseEntity<Endereco> endereco(@RequestParam(value = "cep", required = true) String cep) {
//
//		StringBuilder url = new StringBuilder(urlBuscaPorCep);
//		url.append(cep);
//		url.append("/json/");
//
//		restTemplate = new RestTemplate();
//		ResponseEntity<Endereco> endereco = restTemplate.getForEntity(url.toString(), Endereco.class);
//
//		return ResponseEntity.status(HttpStatus.OK).body(endereco.getBody());
//	}
//
//	@GetMapping("/estados")
//	ResponseEntity<List<Estado>> estados() {
//
//		restTemplate = new RestTemplate();
//		ResponseEntity<Estado[]> endereco = restTemplate.getForEntity(urlBuscaEstados, Estado[].class);
//
//		List<Estado> lista = cadastroService.ordenaEstados(endereco.getBody());
//
//		return ResponseEntity.status(HttpStatus.OK).body(lista);
//	}
//
//	@GetMapping("/municipios")
//	ResponseEntity<Municipio[]> municipios(@RequestParam(value = "id", required = true) String idEstado) {
//
//		StringBuilder url = new StringBuilder(urlBuscaEstados);
//		url.append(idEstado);
//		url.append("/municipios");
//
//		restTemplate = new RestTemplate();
//		ResponseEntity<Municipio[]> endereco = restTemplate.getForEntity(url.toString(), Municipio[].class);
//
//		return ResponseEntity.status(HttpStatus.OK).body(endereco.getBody());
//	}
//
//	@PutMapping(value = "/atualiza-cliente/{cpf}")
//	public ResponseEntity<Cliente> updateUser(@PathVariable(required = true) String cpf,
//			@RequestBody Endereco endereco) {
//
//		Cliente cliente = this.cadastroService.atualizaEndereco(cpf, endereco);
//
//		return ResponseEntity.status(HttpStatus.OK).body(cliente);
//	} 

}
