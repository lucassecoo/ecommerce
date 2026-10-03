package com.ecommerceApi.ecommerce.repositories;

import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(@NotNull @Email @NotEmpty @NotBlank String email);

    Usuario getUsuarioByEmail(String email);

    boolean existsByEmail(String email);
}
