package com.goldenrace.tickets.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.goldenrace.tickets.entities.Detail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class DetailDto {

    private Long detailId;

    @NotNull(message = "Description is required.")
    private String description;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double detailAmount;

    public Detail getDetail() {
        return Detail.builder().idDetail(detailId)
                               .description(description)
                               .amount(detailAmount)
                               .build();
    }

}
