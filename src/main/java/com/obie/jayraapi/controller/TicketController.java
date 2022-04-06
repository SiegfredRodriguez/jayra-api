package com.obie.jayraapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private List<Ticket> ticketlist = new ArrayList<>();

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

    @GetMapping("/getAllTickets")
    public List<Ticket> getAllTickets() {
        if ((ticketlist == null) || ticketlist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ticketlist;
    }

    @PostMapping("/clearTicketList")
    public void clearTicketList() {
        ticketlist.clear();
    }

    @GetMapping("/getTicketListNum")
    public int getTicketListNum() {
        return ticketlist.size();
    }
}
