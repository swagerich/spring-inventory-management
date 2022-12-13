package com.erich.management.Entity;

import com.erich.management.Utils.Enum.SourceStock;
import com.erich.management.Utils.Enum.TypeStock;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock extends AbstractEntity {

    @JoinColumn(name = "date_stock")
    private Instant dateStock;

    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "id_articulo")
    private Article article;

    @Column(name = "type_stock")
    @Enumerated(EnumType.STRING)
    private TypeStock typeStock;

    @Column(name = "source_stock")
    @Enumerated(EnumType.STRING)
    private SourceStock sourceStock;

    @Column(name = "id_empresa")
    private Long idEntreprise;
}
