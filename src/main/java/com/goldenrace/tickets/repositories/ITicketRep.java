package com.goldenrace.tickets.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.goldenrace.tickets.entities.Ticket;

@Repository
public interface ITicketRep extends JpaRepository<Ticket, Long> {

	@Query(value="Select T.* From tickets T "+
	             "Where T.create_at >= :dateIni and T.create_at <= :dateEnd", nativeQuery = true)
	public List<Ticket> findByBetweenDates(Date dateIni, Date dateEnd);

	public Ticket findByIdTicket(Long idTicket);

}
