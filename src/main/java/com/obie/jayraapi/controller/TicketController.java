package com.obie.jayraapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private Ticket ticket;

    @GetMapping("/{ticketNum}")
    public Ticket getTicket(@PathVariable int ticketNum) {
        if (ticket == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (ticket.getTicketNum() == ticketNum) {
            return ticket;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{ticketNum}/{title}")
    public void createTicket(@PathVariable int ticketNum, @PathVariable String title) {
        ticket = new Ticket(ticketNum, title);
    }
}
