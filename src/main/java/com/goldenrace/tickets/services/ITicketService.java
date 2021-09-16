package com.goldenrace.tickets.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public interface ITicketService {

	/**
	 * Find ticket with ours details.
	 * 
	 * @param idTicket Ticket id.
	 * @return Ticket or <b>null</b> if not found anything.
	 */
	public Ticket findByIdTicket(Long idTicket);

	/**
	 * Find ticket with ours details between dates.
	 * 
	 * @param idTicket Ticket id.
	 * @return Ticket or <b>null</b> if not found anything.
	 */
	public List<Ticket> findByIdTicket(Date dateIni, Date dateEnd);

	/**
	 * Create a new ticket.
	 * 
	 * @param ticket Ticket to create.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse createTicket(Ticket ticket);

	/**
	 * Update a existing ticket.
	 * 
	 * @param ticket Ticket to update.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse updateTicket(Ticket ticket);

	/**
	 * Delete a existing ticket.
	 * 
	 * @param idTicket Ticket id to delete.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse deleteTicket(Long idTicket);

}
