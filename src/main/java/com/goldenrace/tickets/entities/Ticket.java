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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(position=1, dataType="Long", value="Ticket id. <br>", example="2001", required=true)
    private Long idTicket;

    @Column(name="create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(position=2, dataType="Date", value="Ticket creation date. (yyyy-MM-dd HH:mm:ss)<br>", example="2021-09-23 15:35:45", required=false)
    private Date createAt;

    @Column(name="total_amount")
    @NotNull(message = "Total amount is required.")
    @Positive(message = "Total amount should be positive value. ")
    @ApiModelProperty(position=3, dataType="Double", value="Total amount ticket. <br>", example="350.25", required=true)
    private Double totalAmount;

    @OneToMany(mappedBy="ticket", fetch = FetchType.LAZY)
    @ApiModelProperty(position=4, dataType="Detail", value="Ticket details bet.", required=false)
    private List<Detail> details;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    @JsonIgnore
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
