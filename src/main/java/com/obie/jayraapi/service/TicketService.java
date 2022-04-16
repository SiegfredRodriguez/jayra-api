package com.obie.jayraapi.service;

import com.obie.jayraapi.datasource.Ticket;
import com.obie.jayraapi.datasource.TicketRepository;
import com.obie.jayraapi.service.exception.TicketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket findTicket(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket createTicket(Ticket ticket) {
        Ticket localCopy = new Ticket(UUID.randomUUID(), ticket.getTitle());
        return ticketRepository.save(localCopy);
    }

    public Ticket updateTicket(Ticket ticket) {
        Ticket localCopy = findTicket(ticket.getId());
        localCopy.setTitle(ticket.getTitle());
        return ticketRepository.save(localCopy);
    }

    public boolean deleteTicket(UUID id) {
        Ticket localCopy = findTicket(id);
        ticketRepository.deleteById(localCopy.getId());
        return true;
    }

}
