package com.ecommerceApi.ecommerce.models.carrinho;

import com.ecommerceApi.ecommerce.models.itemCarrinho.ItemCarrinho;
import com.ecommerceApi.ecommerce.models.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
@NoArgsConstructor
@Getter
@Setter
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private BigDecimal valorTotal;

    @OneToMany(
            mappedBy = "carrinho",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemCarrinho> itens = new ArrayList<>();
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
    }

    public void removerItem(ItemCarrinho item) {
        itens.remove(item);
    }

    public void limparCarrinho(){
        itens.clear();
    }

}
