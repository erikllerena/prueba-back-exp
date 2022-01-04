package com.example.pruebatecnica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pruebatecnica.entity.Client;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
	Optional<Client> findByClientId(String clientId);
	Optional<Client> findByClientIdAndClientSecret(String clientId, String clientSecret);
	Optional<Client> findByTokenAlfa(String token);
}
