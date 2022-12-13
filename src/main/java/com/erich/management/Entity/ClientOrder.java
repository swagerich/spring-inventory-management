package com.erich.management.Entity;

import com.erich.management.Utils.Enum.OrdeStatus;
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
@Table(name = "pedido_cliente")
public class ClientOrder extends AbstractEntity{

    private String code;

    @Column(name = "date_order")
    private Instant dateOrder;

    @Column(name = "orde_status")
    @Enumerated(EnumType.STRING)
    private OrdeStatus ordeStatus;

    @Column(name = "id_empresa")
    private Long idEntreprise;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Client client;

    @OneToMany(mappedBy = "clientOrder")
    private List<ClientOrderLine> clientOrderLine;
}
