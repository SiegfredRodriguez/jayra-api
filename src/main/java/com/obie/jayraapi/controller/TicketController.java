package com.obie.jayraapi.controller;

import com.obie.jayraapi.datasource.Ticket;
import com.obie.jayraapi.datasource.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private List<Ticket> ticketlist = new ArrayList<>();
    private TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/dummy")
    public String dummyKo() {
        return ticketRepository.findById(UUID.fromString("f3c5c58c-bd25-4a8e-be06-30258f16822d")).get().getTitle();
    }

    @GetMapping("/{ticketNum}")
    public ResponseEntity<Ticket> getTicket(@PathVariable UUID ticketNum) {
        Optional<com.obie.jayraapi.datasource.Ticket> data = ticketRepository.findById(ticketNum);
        if (data.isPresent()) {
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/createTicket/{title}")
    public ResponseEntity<Ticket> createTicket(@PathVariable String title) {
        UUID uuid = UUID.randomUUID();
        Ticket ticket = new Ticket();
        ticket.setId(uuid);
        ticket.setTitle(title);
        try {
            Ticket ticketRet = ticketRepository.save(ticket);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{ticketNum}/{title}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable UUID ticketNum, @PathVariable String title) {
        Optional<Ticket> data = ticketRepository.findById(ticketNum);
        if (data.isPresent()) {
            Ticket ticket = data.get();
            ticket.setTitle(title);
            return new ResponseEntity<>(ticketRepository.save(ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{ticketNum}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable(value = "ticketNum") UUID ticketNum) {
        try {
            ticketRepository.deleteById(ticketNum);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
