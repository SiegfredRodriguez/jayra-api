package com.obie.jayraapi.controller;

import com.obie.jayraapi.datasource.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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
    public Ticket getTicket(@PathVariable int ticketNum) {
        if ((ticketlist == null) || ticketlist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ticket ticketRet = null;
        for (Ticket ticket : ticketlist) {
            if (ticket.getTicketNum() == ticketNum) {
                ticketRet = ticket;
            }
        }
        if (ticketRet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ticketRet;
    }

    @PostMapping("/createTicket/{ticketNum}/{title}")
    public void createTicket(@PathVariable int ticketNum, @PathVariable String title) {
        Ticket ticket = new Ticket(ticketNum, title);
        ticketlist.add(ticket);
    }

    @PatchMapping("/{ticketNum}/{title}")
    public void updateTicket(@PathVariable int ticketNum, @PathVariable String title) {
        if ((ticketlist == null) || ticketlist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        for (Ticket ticket : ticketlist) {
            if (ticket.getTicketNum() == ticketNum) {
                ticket.setTitle(title);
            }
        }
    }

    @DeleteMapping("/{ticketNum}")
    public Map<String, Boolean> deleteTicket(@PathVariable(value = "ticketNum") int ticketNum) {
        if ((ticketlist == null) || ticketlist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Map<String, Boolean> response = new HashMap<>();

        for (int i = 0; i < ticketlist.size(); i++) {
            Ticket ticket = ticketlist.get(i);
            if (ticket.getTicketNum() == ticketNum) {
                ticketlist.remove(i);
                response.put("deleted", Boolean.TRUE);
            }
        }
        if (response.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
