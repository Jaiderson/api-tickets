package com.goldenrace.tickets.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TicketDto {

    private Long idTicket;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double totalAmount;

    private List<Detail> details = Lists.newArrayList();

    public Ticket getTicket() {
        return Ticket.builder().idTicket(idTicket)
                               .totalAmount(totalAmount)
                               .details(details)
                               .build();
    }

}
