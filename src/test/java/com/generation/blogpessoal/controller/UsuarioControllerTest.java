package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start(){

        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Root","root@root.com","rootroot", " "));
    }


    @Test
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario(){

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>
                (new Usuario(0L, "Deivison Rocha","deivi@gmail.com","12345678","  "));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(),corpoResposta.getBody().getUsuario());

    }

    @Test
    @DisplayName("Listar todos os usuarios")
    public void deveMostrarTodosUsuarios(){

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "teste01","testetes01@gmail.com","12345678", " "));
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "teste02","teste02@gmail.com","fghghfgh1142", " "));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com","rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK,  resposta.getStatusCode());
    }



    @Test
    @DisplayName("Atualizar um usuário")
    public void deveAtualizarUmUsuario(){

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Juliana teste 01", "juju@gmail.com","juju12345678", "  "));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
        "Juliana teste 02 ", "juju@gmail.com","juju12345678", "  ");
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

                ResponseEntity<Usuario>  corpoResposta= testRestTemplate
                .withBasicAuth("root@root.com","rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

                assertEquals(HttpStatus.OK,corpoResposta.getStatusCode());
                assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
                assertEquals(corpoRequisicao.getBody().getUsuario(),corpoResposta.getBody().getUsuario());
    }

    @Test
    @DisplayName("filtrar por ID")
    public void devebuscarUsuarioPorId() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/2", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Login ")
    public void Login() {
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "test09","testetes09@gmail.com","12345678", " "));

        HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<>(
                new UsuarioLogin("testetes09@gmail.com", "12345678"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

}
