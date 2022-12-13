package com.erich.management.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Category extends AbstractEntity {

    private String code;

    private String description;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

}
