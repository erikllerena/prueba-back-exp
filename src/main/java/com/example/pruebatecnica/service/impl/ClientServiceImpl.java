package com.example.pruebatecnica.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pruebatecnica.entity.Client;
import com.example.pruebatecnica.repository.IClientRepository;
import com.example.pruebatecnica.service.IClientService;
import com.example.pruebatecnica.utils.Functions;

@Service
public class ClientServiceImpl implements IClientService {
	
	@Autowired
	IClientRepository clientRepository;

	@Override
	public Optional<Client> findByClientId(String clientId) {
		return clientRepository.findByClientId(clientId);
	}

	@Override
	public Optional<Client> generarToken(String clientId, String clientSecret) {		
		Optional<Client> optCli = clientRepository.findByClientId(clientId);
		if(optCli.isPresent()) {
			optCli.get().setTokenAlfa(Functions.getRandomString());
			optCli.get().setJwtToken(Functions.obtenerToken(clientId));
			clientRepository.save(optCli.get());
		}		
		return optCli;
	}

	@Override
	public Optional<Client> findByToken(String token) {
		Optional<Client> optCli = clientRepository.findByTokenAlfa(token);		
		return optCli;
	}
}
