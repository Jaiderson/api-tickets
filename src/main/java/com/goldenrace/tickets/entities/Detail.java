package com.goldenrace.tickets.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="details")
@NoArgsConstructor @AllArgsConstructor 
@Builder @Getter @Setter @ToString
@Data
public class Detail {

    @Id
    @Column(name="detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetail;

    @Column(name="description")
    @NotNull(message = "Description is required.")
    private Date description;

    @Column(name="amount")
    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double amount;

}
