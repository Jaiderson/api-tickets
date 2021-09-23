package com.goldenrace.tickets.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goldenrace.tickets.entities.Detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
public class DetailDto {

	@ApiModelProperty(position=1, dataType="Long", value="Detail id. <br>", example="2001", required=false)
    private Long detailId;

    @NotNull(message = "Description is required.")
    @ApiModelProperty(position=2, dataType="String", value="Detail description bet. <br>", 
                      example="Champion league bet [Barca vs Real Madrid].", required=true)
    private String description;

    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    @ApiModelProperty(position=3, dataType="Double", value="Amount detail. <br>", example="350.25", required=true)
    private Double detailAmount;

    @JsonIgnore
    public Detail getDetail() {
        return Detail.builder().idDetail(detailId)
                               .description(description)
                               .amount(detailAmount)
                               .build();
    }

}
