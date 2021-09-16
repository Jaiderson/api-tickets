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

    private Long ticketId;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double total;

    private List<DetailDto> details = Lists.newArrayList();

    public Ticket getTicket() {
    	List<Detail> detailList = Lists.newArrayList(); 
    	details.stream().forEach(detail -> {
    		detailList.add(detail.getDetail());
    	});

    	return Ticket.builder().idTicket(ticketId)
                               .totalAmount(total)
                               .details(detailList)
                               .build();
    }

}
