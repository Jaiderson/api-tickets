package com.goldenrace.tickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenrace.tickets.entities.Ticket;

@Repository
public interface ITicketRep extends JpaRepository<Ticket, Long> {

}
