package com.aulageneration.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aulageneration.blogPessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private Usuario usuario;
	private Usuario usuarioAlterar;
	
	@BeforeAll
	public void start() {
		usuario = new Usuario("MariaLuisa","134652");
		usuarioAlterar = new Usuario("MariaLuisa", "654321");
	}


	@Test
	void deveSalvarUsuarioRetornaStatus201() {
		
		/*
		 * Criando um objeto do tipo HttpEntity para enviar como terceiro
		 * parâmentro do método exchange. (Enviando um objeto usuario via body)
		 * 
		 * */
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuario/salvar", HttpMethod.POST, request, Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	
	@Test
	void deveRetornarListadeUsuarioRetornaStatus200() {
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("MariaLuisa", "134652")
				.exchange("/usuario/todos", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	
	@Test
	void deveAtualizarSenhaUsuarioRetornaStatus201() {
		
		/*
		 * Criando um objeto do tipo HttpEntity para enviar como terceiro
		 * parâmentro do método exchange. (Enviando um objeto usuario via body)
		 * 
		 * */
		HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioAlterar);
		
		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("MariaLuisa", "134652")
				.exchange("/usuario/alterar", HttpMethod.PUT, request, Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
		
}
