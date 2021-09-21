package com.goldenrace.tickets.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public interface ITicketService {

	public List<Ticket> findAllTickets();

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
	 * Update total amount of a ticket.
	 * 
	 * @param idTicket Ticket id.
	 * @param value Value to sum.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse updateTotalAmountTicket(Long idTicket, double value);

	/**
	 * Add a new detail to existing ticket and update total amount.
	 * 
	 * @param idTicket Ticket id to add new detail.
	 * @param detail New detail ticket.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse addDetailTicket(Long idTicket, Detail detail);

	/**
	 * Delete a detail of a existing ticket and update total amount.
	 * 
	 * @param idTicket Ticket id to remove a detail.
	 * @param idDetail Detail id to delete.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse deleteDetailTicket(Long idTicket, Long idDetail);

	/**
	 * Delete a existing ticket.
	 * 
	 * @param idTicket Ticket id to delete.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse deleteTicket(Long idTicket);

}
