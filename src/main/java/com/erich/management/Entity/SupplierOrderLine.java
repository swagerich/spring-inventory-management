package com.erich.management.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "linea_pedido_proveedor")
public class SupplierOrderLine extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "id_pedido_proveedor")
    private SupplierOrder supplierOrder;

    private BigDecimal quantity;

    @Column(name = "price_unitario")
    private BigDecimal priceUnitario;

    @Column(name = "id_empresa")
    private Long idEntreprise;

}
