package com.erich.management.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Sales extends AbstractEntity{

    private String code;

    @Column(name = "sale_date")
    private Instant saleDate;

    private String comments;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @OneToMany(mappedBy ="sale")
    private List<SaleLine> saleLines;


}
