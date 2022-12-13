package com.erich.management.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proveedor")
public class Supplier extends AbstractEntity{

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String photo;

    private String email;

    private String phone;

    @Embedded
    private Address address;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @OneToMany(mappedBy = "supplier")
    private List<SupplierOrder> supplierOrder;
}
