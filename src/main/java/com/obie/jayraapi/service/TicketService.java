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

    public Optional<Ticket> helperFindTicket(UUID id) {
        return ticketRepository.findById(id);
    }

    public Ticket findTicket(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setId(UUID.randomUUID());
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Ticket ticket) {
        var result = helperFindTicket(ticket.getId());

        if (result.isPresent()) {
            var t = result.get();
            t.setTitle(ticket.getTitle());
            return ticketRepository.save(t);
        } else {
            return null;
        }
    }

    public boolean deleteTicket(UUID id) {
        try {
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
