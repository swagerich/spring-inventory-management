package com.erich.management.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linea_pedido_cliente")
public class ClientOrderLine extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "id_pedido_cliente")
    private ClientOrder clientOrder;

    private BigDecimal quantity;

    @Column(name = "price_unitario")
    private BigDecimal priceUnitario;

    @Column(name = "id_empresa")
    private Long idEntreprise;
}
