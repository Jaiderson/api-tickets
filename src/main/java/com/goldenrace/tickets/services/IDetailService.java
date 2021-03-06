package com.goldenrace.tickets.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public interface IDetailService {

	/**
	 * Find a details of a ticket by ticket id and detail Id.
	 * 
	 * @param idDetail Detail id.
	 * @return Detail of a ticktet or <b>null</b> if not found anything.
	 */
	public Detail findByIdDetail(Long idDetail); 

	/**
	 * Find a details of a ticket by ticket id and detail Id.
	 * 
	 * @param idTicket Ticket
	 * @param idDetail Detail id.
	 * @return Detail of a ticktet or <b>null</b> if not found anything.
	 */
	public Detail findByIdTicketAndIdDetail(Long idTicket, Long idDetail); 

	/**
	 * Find all details of a ticket by ticket Id.
	 * 
	 * @param idTicket Ticket id.
	 * @return List of details or empty list if not found anything.
	 */
	public List<Detail> findByIdTicket(Long idTicket); 

	/**
	 * Create a new ticket detail.
	 * 
	 * @param detail Detail to create.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse createDetail(Detail detail);

	/**
	 * Update a ticket detail.
	 * 
	 * @param detail Detail to update.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse updateDetail(Detail detail);

	/**
	 * Delete a ticket detail.
	 * 
	 * @param idDetail Detail id to delete.
	 * @return Message response whit OK or bat status.
	 */
	public MessageResponse deleteDetail(Long idDetail);

   /**
     * Delete all details of a existing ticket.
     * 
     * @param idTicket Ticket id to delete ours details.
     * @return Message response whit OK or bat status.
     */
    public MessageResponse deleteDetailsByIdTicket(Long idTicket);

}
