package com.obie.jayraapi.controller;

import com.obie.jayraapi.datasource.Ticket;
import com.obie.jayraapi.service.TicketService;
import com.obie.jayraapi.service.exception.TicketNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketNum}")
    public Ticket getTicket(@PathVariable UUID ticketNum) {
        return ticketService.findTicket(ticketNum);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDTO.getTitle());

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

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorMessage> ticketNotFound(TicketNotFoundException ticketNotFoundException) {

        ErrorMessage message = new ErrorMessage();
        message.setMessage("Did not find ticket " + ticketNotFoundException.getUuid().toString());
        message.setCode(HttpStatus.NOT_FOUND.value());

        log.info("Did not find " + ticketNotFoundException.getUuid().toString());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}
