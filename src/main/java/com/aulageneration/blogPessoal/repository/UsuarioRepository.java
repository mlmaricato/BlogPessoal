package com.aulageneration.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aulageneration.blogPessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsuario(String usuario);
	List<Usuario> findAllByUsuarioContainingIgnoreCase(String usuario);

}
