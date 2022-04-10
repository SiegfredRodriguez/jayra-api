package com.obie.jayraapi.datasource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    //Ticket findAllByIdAAndTitleOr
}
