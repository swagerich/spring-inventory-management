package com.erich.management.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "username")
public class User  extends AbstractEntity{

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    private String photo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Enterprise enterprise;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private List<Roles> roles;
}
