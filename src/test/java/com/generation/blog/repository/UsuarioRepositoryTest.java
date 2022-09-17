package com.generation.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

/*indica que a classe UsuarioRepositoryTest é uma classe de test,
 e que esse test será rodado em uma porta aleatoria local no meu computador
 (desde que ela não esteja já sendo usada)*/

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

/*indica que o teste a ser feito vai ser um teste unitario(por classe)*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
/*inserindo usuarios no meu banco de dados h2,
  para que eu possa testar as funções de procurar um usuario por 
  nome e por email*/
	@BeforeAll
	void start() {
		
		usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario(0L, "João Silva", "joao@email.com.br", "11111111", "https://i.imgur.com/FETvs20.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela Silva", "manuela@email.com.br", "22222222", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana Silva", "adriana@email.com.br", "33333333", "https://i.imgur.com/mB3VM2N.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "44444444", "https://i.imgur.com/JR7kUFU.jpg"));
	}
	
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		// Aqui é o que eu espero receber da API, buscando um usuario pelo seu e-mail
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		// compara, me mostrando o que eu recebi em comparação com o que eu gostaria de receber
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana Silva"));
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}

}
