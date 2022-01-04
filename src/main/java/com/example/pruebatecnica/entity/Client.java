package com.example.pruebatecnica.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_client")
public class Client {
	@Id
	@Column(name = "identityClient")
	private Long identityClient;
	
	@Column(name = "clientId")
	private String clientId;
	
	@Column(name = "clientSecret")
	private String clientSecret;
	
	@Column(name = "tokenAlfa")
	private String tokenAlfa;
	
	@Column(name = "jwtToken")
	private String jwtToken;	
}
