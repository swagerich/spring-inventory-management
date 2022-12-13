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
@Table(name = "linea_venta")
public class SaleLine extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_ventas")
    private Sales sale;

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Article article;

    private BigDecimal quantity;

    @Column(name = "price_unitario")
    private BigDecimal priceUnitario;

    @Column(name = "id_empresa")
    private Long idEntreprise;

}
