package com.goldenrace.tickets.services.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.repositories.ITicketRep;
import com.goldenrace.tickets.services.ITicketService;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public class TicketServiceImpl implements ITicketService {

	@Autowired
	private ITicketRep ticketRep;

	@Override
	public Ticket findByIdTicket(Long idTicket) {
		return ticketRep.findByIdTicket(idTicket);
	}

	@Override
	public List<Ticket> findByIdTicket(Date dateIni, Date dateEnd) {
		return ticketRep.findByBetweenDates(dateIni, dateEnd);
	}

	@Override
	public MessageResponse createTicket(Ticket newTicket) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket libroBd = findByIdTicket(newTicket.getIdTicket());

		if(null == libroBd && this.saveTicket(msnResponse, newTicket)) {
	        msnResponse.setStatus(MessageResponse.CREATED_OK);
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.ALREADY_EXIST);
			msnResponse.setStatus(MessageResponse.ALREADY_EXIST);
		}
		return msnResponse;
	}

	@Override
	public MessageResponse updateTicket(Ticket ticket) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket ticketDb = findByIdTicket(ticket.getIdTicket());

		if(null != ticketDb && this.saveTicket(msnResponse, ticket)) {
	        msnResponse.setStatus(MessageResponse.PROCESS_OK);
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	@Override
	public MessageResponse deleteTicket(Long idTicket) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket ticketDb = findByIdTicket(idTicket);
		if(null != ticketDb) {
		    try {
		    	ticketRep.delete(ticketDb);
		    	msnResponse.setStatus(MessageResponse.PROCESS_OK);
		    }
		    catch (Exception e) {
		    	msnResponse.getInconsistencies().add(MessageResponse.SQL_ERROR + " "+ e.getMessage());
		    	msnResponse.setStatus(MessageResponse.SQL_ERROR);
			}
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	/**
	 * Method saved a ticket and catch the inconsistencies if existing.
	 * 
	 * @param msnResponse Object with the inconsistencies if existing.
	 * @param ticket Ticket to save.
	 */
	private boolean saveTicket(MessageResponse msnResponse, Ticket ticket) {
	    boolean isOk = false;
		try {
			ticketRep.save(ticket);
			isOk = true;
		}
		catch (Exception e) {
			isOk = false;
			msnResponse.getInconsistencies().add(MessageResponse.SQL_ERROR + " "+ e.getMessage());
			msnResponse.setStatus(MessageResponse.SQL_ERROR);
		}
		return isOk;
	}

}
