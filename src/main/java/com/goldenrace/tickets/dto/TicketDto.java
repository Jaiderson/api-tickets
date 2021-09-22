package com.goldenrace.tickets.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
public class TicketDto {

	@ApiModelProperty(position=1, dataType="Long", value="Ticket id. <br>", example="201", required=false)
    private Long ticketId;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    @ApiModelProperty(position=2, dataType="Double", value="Total amount ticket. <br>", example="350.25", required=true)
    private Double total;

    @ApiModelProperty(position=3, dataType="Detail")
    private List<DetailDto> details = Lists.newArrayList();

    @JsonIgnore
    public Ticket getTicket() {
    	List<Detail> detailList = Lists.newArrayList(); 
    	details.stream().forEach(detail -> detailList.add(detail.getDetail()));

    	return Ticket.builder().idTicket(ticketId)
                               .totalAmount(total)
                               .details(detailList)
                               .build();
    }

}
