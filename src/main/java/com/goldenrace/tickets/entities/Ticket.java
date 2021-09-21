package com.goldenrace.tickets.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="tickets")
@NoArgsConstructor @AllArgsConstructor 
@Builder @Getter @Setter @ToString
@Data
public class Ticket {

    @Id
    @Column(name="ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;

    @Column(name="create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(name="total_amount")
    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    private Double totalAmount;

    @OneToMany(mappedBy="ticket", fetch = FetchType.LAZY)
    private List<Detail> details;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public boolean isOkTotalAmount() {
    	double total = 0; 
    	if(null != details && !details.isEmpty()) {
    		for(Detail detail : details) {
    			total += detail.getAmount();
    		}
    	}
    	return totalAmount == total;
    }
}
