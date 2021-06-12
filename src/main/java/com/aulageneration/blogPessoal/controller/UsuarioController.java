package com.aulageneration.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aulageneration.blogPessoal.model.Usuario;
import com.aulageneration.blogPessoal.model.UsuarioLogin;
import com.aulageneration.blogPessoal.repository.UsuarioRepository;
import com.aulageneration.blogPessoal.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	private UsuarioRepository repository;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Usuario>> pegarTodos(){
		List<Usuario> listaDeUsuario = repository.findAll();
		if (!listaDeUsuario.isEmpty()) {
			return ResponseEntity.status(200).body(listaDeUsuario);
		} else {			
			return ResponseEntity.status(204).build();
		}
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Object> salvarUsuario(@Valid @RequestBody Usuario novoUsuario){
		return repository.findByUsuario(novoUsuario.getUsuario())
				.map(usuarioExistente -> {
					return ResponseEntity.status(400).build();
				})
				.orElseGet(() ->{
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					String senhaCriptografada = encoder.encode(novoUsuario.getSenha());
					novoUsuario.setSenha(senhaCriptografada);
					return ResponseEntity.status(201).body(repository.save(novoUsuario));
				});
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> Autentication(@RequestBody Optional<UsuarioLogin> user) {
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> post(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioService.cadastrarUsuario(usuario));
	}

}
