package com.obie.jayraapi.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketNotFoundException extends TicketServiceException {
    private final UUID uuid;

    public TicketNotFoundException(UUID uuid) {
        this.uuid = uuid;
    }
}
