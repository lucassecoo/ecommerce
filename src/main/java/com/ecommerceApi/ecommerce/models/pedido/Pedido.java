package com.ecommerceApi.ecommerce.models.pedido;

import com.ecommerceApi.ecommerce.models.endereco.Endereco;
import com.ecommerceApi.ecommerce.models.itemPedido.ItemPedido;
import com.ecommerceApi.ecommerce.models.statusPedido.StatusPedido;
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
@Table(name = "pedidos")
@NoArgsConstructor
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_entrega_id", nullable = false)
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    private LocalDateTime dataPedido;

    @PrePersist
    public void prePersist() {
        dataPedido = LocalDateTime.now();
    }
}
