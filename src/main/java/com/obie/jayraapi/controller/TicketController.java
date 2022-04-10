package com.obie.jayraapi.controller;

import com.obie.jayraapi.datasource.Ticket;
import com.obie.jayraapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketNum}")
    public ResponseEntity<Ticket> getTicket(@PathVariable UUID ticketNum) {
        Optional<com.obie.jayraapi.datasource.Ticket> data = ticketService.findTicket(ticketNum);
        if (data.isPresent()) {
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/createTicket/{title}")
    public ResponseEntity<Ticket> createTicket(@PathVariable String title) {
        Ticket ticket = new Ticket();
        ticket.setTitle(title);

        try {
            Ticket ticketRet = ticketService.createTicket(ticket);
            return new ResponseEntity<>(ticketRet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ticketNum}/{title}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable UUID ticketNum, @PathVariable String title) {

        Ticket ticket = new Ticket(ticketNum, title);
        ticket = ticketService.updateTicket(ticket);

        if (Objects.isNull(ticket)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @DeleteMapping("/{ticketNum}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable(value = "ticketNum") UUID ticketNum) {
        if (ticketService.deleteTicket(ticketNum)) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
