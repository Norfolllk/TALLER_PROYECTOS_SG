package com.krakedev.proyectos.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {

        try {

            Usuario nuevo = usuarioService.registrar(usuario);

            // solo datos públicos: nunca se devuelve la contraseña ni su hash
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "id", nuevo.getId(),
                    "username", nuevo.getUsername(),
                    "rol", nuevo.getRol()
            ));

        } catch (IllegalStateException e) {

            // el nombre de usuario ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        } catch (IllegalArgumentException e) {

            // datos inválidos o rol no permitido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar usuario");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario datosLogin) {

        try {

            Optional<Usuario> usuario = usuarioService.login(
                    datosLogin.getUsername(),
                    datosLogin.getPassword()
            );

            if (usuario.isPresent()) {

                Usuario usuarioAutenticado = usuario.get();

                return ResponseEntity.ok(Map.of(
                        "username", usuarioAutenticado.getUsername(),
                        "rol", usuarioAutenticado.getRol(),
                        "mensaje", "Login exitoso"
                ));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }

}
