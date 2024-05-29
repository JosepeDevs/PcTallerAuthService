package com.josepdevs.Application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.josepdevs.Domain.dto.AuthenticationRequest;
import com.josepdevs.Domain.dto.AuthenticationResponse;
import com.josepdevs.Domain.service.JwtTokenReaderAndIssuerService;
import com.josepdevs.Infra.output.AuthJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Login {

	private final AuthenticationManager authenticationManager;
	private final AuthJpaRepository repository;
	private final JwtTokenReaderAndIssuerService jwtService;


	public AuthenticationResponse login(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPsswrd()));
		//if we arrive here the previous line is correct
		var userDataAuth = repository.findByUsername(request.getUsername()).orElseThrow(); //todo que va a thorwear esto??
		var jwtToken = jwtService.generateToken(userDataAuth);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
}
