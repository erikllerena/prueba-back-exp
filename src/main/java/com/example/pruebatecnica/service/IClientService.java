package com.example.pruebatecnica.service;

import java.util.Optional;

import com.example.pruebatecnica.entity.Client;

public interface IClientService {
	Optional<Client> findByClientId(String clientId);
	Optional<Client> generarToken(String clientId, String clientSecret);
	Optional<Client> findByToken(String token);
}
