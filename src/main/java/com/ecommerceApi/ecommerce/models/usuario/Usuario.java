package com.ecommerceApi.ecommerce.models.usuario;

import com.ecommerceApi.ecommerce.models.carrinho.Carrinho;
import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import com.ecommerceApi.ecommerce.models.pedido.Pedido;
import com.ecommerceApi.ecommerce.models.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    private String senhaHash;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime criadoEm;

    @OneToOne(mappedBy = "usuario")
    private Carrinho carrinho;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Endereco> enderecos = new ArrayList<>();

    public Usuario(UsuarioCadastroDto dto) {
        this.email = dto.email();
        this.nome = dto.nome();
        this.senhaHash = dto.senha();
        this.telefone = dto.telefone();
        this.role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
