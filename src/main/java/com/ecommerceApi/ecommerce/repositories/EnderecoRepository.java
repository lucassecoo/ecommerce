package com.ecommerceApi.ecommerce.repositories;

import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findAllByUsuarioIdOrderByPrincipalDescIdAsc(Long usuarioId, Pageable pageable);
    Optional<Endereco> findByIdAndUsuarioId(Long id, Long usuarioId);

    Optional<Endereco> findFirstByUsuarioId(Long usuarioId);
}
