package com.erich.management.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articulo")
public class Article extends AbstractEntity {

    @Column(name = "code_article")
    private String codeArticle;

    private String description;

    @Column(name = "price_unitario")
    private BigDecimal priceUnitario;

    private BigDecimal tax;

    @Column(name = "price_unitario_ttc")
    private BigDecimal priceUnitarioTtc;

    private String photo;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<SaleLine> salesLine;

    @OneToMany(mappedBy = "article")
    private List<ClientOrderLine> clientOrderLines;

    @OneToMany(mappedBy = "article")
    private List<SupplierOrderLine> supplierOrderLines;

    @OneToMany(mappedBy = "article")
    private List<Stock> stocks;

}
