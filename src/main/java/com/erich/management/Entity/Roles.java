package com.erich.management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles extends  AbstractEntity{

    @JoinColumn(name = "roles_name")
    private String rolesName;

    @ManyToOne
    @JoinColumn(name = "id_username")
    private User user;
}
