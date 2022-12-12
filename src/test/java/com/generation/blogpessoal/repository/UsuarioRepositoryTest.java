package com.generation.blogpessoal.repository;


import com.generation.blogpessoal.controller.UsuarioController;
import com.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;



    @BeforeAll
    void start (){

        usuarioRepository.deleteAll();
        usuarioRepository.save(new Usuario( 0L,
                "João da Silva", "joao.silva@gmail.com", "123456789"," "  ));
        usuarioRepository.save(new Usuario( 0L,
                "Maria da Silva", "Maria.silva@gmail.com", "8795358147"," "  ));
        usuarioRepository.save(new Usuario( 0L,
                "Adriana da Silva", "ij.silva@gmail.com", "122645199"," "  ));
        usuarioRepository.save(new Usuario( 0L,
                "Jubilieu Silva ", "jubilei@gmail.com", "dsa287169117"," "  ));

    }


    @Test
    @DisplayName("retorna 1 usuario")
    public void deveRetornarUmUsuario(){

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao.silva@gmail.com");
        assertTrue(usuario.get().getUsuario().equals("joao.silva@gmail.com"));
    }

    @Test
    @DisplayName("retorna 3 usuarios")
    public void deveRetornarTresUsuarios(){

        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("Joao da Silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Maria da Silva"));
        assertTrue(listaDeUsuarios.get(02).getNome().equals("Adriana da Silva"));
    }


    @AfterAll
    public void end(){

        usuarioRepository.deleteAll();
    }

}
