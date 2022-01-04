package com.example.pruebatecnica.utils;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Functions {
	public static String obtenerToken(String clientId) {
		String token = Jwts.builder()
				.setSubject(clientId)
				.signWith(SignatureAlgorithm.HS512, Constants.CLAVE_SECRETA.getBytes())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(Constants.TIEMPO_EXPIRACION))) //4horas expiración
				.compact();
		return token;
		
	}
	
	public static String getRandomString() {        
        String valoresAlfanumericos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
        
        int valorEntero = (int) Math.floor(Math.random()*(15-30+1)+30); 
        StringBuilder strBuilder = new StringBuilder(valorEntero); 

        for (int i = 0; i < valorEntero; i++) {             
            int calc = (int)(valoresAlfanumericos.length() * Math.random());             
            strBuilder.append(valoresAlfanumericos.charAt(calc)); 
        } 

        return strBuilder.toString(); 
    } 
}
