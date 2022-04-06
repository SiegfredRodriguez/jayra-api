package com.obie.jayraapi.datasource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "jayra_api", name = "ticket")
public class Ticket {

    @Id
    private UUID id;

    @Column
    private String title;


}
