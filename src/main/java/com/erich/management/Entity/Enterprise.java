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
@Table(name = "empresa")
public class Enterprise extends AbstractEntity {

    private String name;

    private String description;

    @Embedded
    private Address address;

    @Column(name = "tax_code")
    private String taxCode;

    private String photo;

    private String email;

    private String phone;

    @Column(name = "web_site")
    private String webSite;

    @OneToMany(mappedBy = "enterprise")
    private List<User> users;

}
