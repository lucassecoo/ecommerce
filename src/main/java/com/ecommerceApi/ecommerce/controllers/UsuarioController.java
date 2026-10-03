package com.ecommerceApi.ecommerce.controllers;

import com.ecommerceApi.ecommerce.models.usuario.UsuarioResponseDTO;
import com.ecommerceApi.ecommerce.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários")
@AllArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @Operation(
            summary = "Buscar usuário por e-mail",
            description = "Retorna os dados de um usuário a partir do seu endereço de e-mail."
    )
    @GetMapping("me")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioByEmail(){
        return ResponseEntity.ok(service.getUsuarioAutenticado());
    }
}
