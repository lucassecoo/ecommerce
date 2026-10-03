package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.usuario.LoginResponseDTO;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioCadastroDto;
import com.ecommerceApi.ecommerce.models.usuario.UsuarioLoginDTO;
import com.ecommerceApi.ecommerce.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @Operation(
            summary = "Realizar login",
            description = "Autentica um usuário utilizando e-mail e senha e retorna um token JWT."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody UsuarioLoginDTO dto){

        return ResponseEntity.ok(
                service.login(dto)
        );
    }

    @Operation(
            summary = "Cadastrar usuário",
            description = "Realiza o cadastro de um novo usuário no sistema."
    )
    @PostMapping("/register")
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDto dto){
        service.cadastrarUsuario(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Usuário autenticado",
            description = "Retorna o e-mail do usuário atualmente autenticado."
    )
    @GetMapping("/me")
    public ResponseEntity<String> me(Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }

    @Operation(
            summary = "Dados do usuário autenticado",
            description = "Retorna todas as informações do usuário autenticado."
    )
    @GetMapping("/usuario-autenticado")
    public ResponseEntity<Usuario> getUsuarioAutenticado(){
        return ResponseEntity.ok(service.getUsuarioAutenticado());
    }
}
