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
@Table(name = "cliente")
public class Client extends AbstractEntity {

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address address;

    private String photo;

    private String email;

    private String phone;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @OneToMany(mappedBy = "client")
    private List<ClientOrder> clientOrder;
}
