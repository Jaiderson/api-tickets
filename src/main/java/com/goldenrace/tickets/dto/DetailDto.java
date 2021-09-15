package com.goldenrace.tickets.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.goldenrace.tickets.entities.Detail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DetailDto {

    private Long idDetail;

    @NotNull(message = "Description is required.")
    private Date description;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double amount;

    public Detail getDetail() {
        return Detail.builder().idDetail(idDetail)
                               .description(description)
                               .amount(amount)
                               .build();
    }

}
