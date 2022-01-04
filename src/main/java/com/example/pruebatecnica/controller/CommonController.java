package com.example.pruebatecnica.controller;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebatecnica.dto.StatusDTO;
import com.example.pruebatecnica.entity.Client;
import com.example.pruebatecnica.response.BaseResponse;
import com.example.pruebatecnica.service.IClientService;
import com.example.pruebatecnica.utils.Constants;

@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@Validated
public class CommonController {

	@Autowired
	IClientService clientService;
	
	@GetMapping("/status")
	public ResponseEntity<?> statusApi() {	
		
		StatusDTO statusDTO = StatusDTO
				.builder()
				.database(Constants.MSG_SUCCESS)
				.disk(Constants.MSG_SUCCESS)
				.network(Constants.MSG_SUCCESS)
				.build();				
		
		return ResponseEntity.ok().body(statusDTO);		
	}
	
	@GetMapping("/client")
	public ResponseEntity<?> getClient(@QueryParam("clientId") String clientId, @QueryParam("clientSecret") String clientSecret) {
		clientService.findByClientId(clientId).orElseThrow(() -> new EntityNotFoundException());
		return ResponseEntity.ok().body(BaseResponse.successNoData(Constants.MSG_SUCCESS));
	}
	
	@GetMapping("token")
	public ResponseEntity<?> getToken(@RequestHeader("clientId") String clientId, @RequestHeader("clientSecret") String clientSecret) {
		Client cli = clientService.generarToken(clientId, clientSecret).orElseThrow(() -> new EntityNotFoundException());
		return ResponseEntity.ok().body(BaseResponse.successWithData(cli, Constants.MSG_SUCCESS));
	}
	
	@GetMapping("introspect")
	public ResponseEntity<?> getClientToken(@RequestHeader("token") String token) {
		Client cli = clientService.findByToken(token).orElseThrow(() -> new EntityNotFoundException());
		return ResponseEntity.ok().body(BaseResponse.successWithData(cli, Constants.MSG_SUCCESS));
	}
}
