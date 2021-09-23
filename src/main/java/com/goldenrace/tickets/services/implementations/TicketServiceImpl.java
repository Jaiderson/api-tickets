package com.goldenrace.tickets.services.implementations;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.repositories.ITicketRep;
import com.goldenrace.tickets.services.IDetailService;
import com.goldenrace.tickets.services.ITicketService;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public class TicketServiceImpl implements ITicketService {

	@Autowired
	private ITicketRep ticketRep;

	@Autowired
	private IDetailService detailService;

	public TicketServiceImpl(ITicketRep ticketRep, IDetailService detailService) {
	    super();
	    this.ticketRep = ticketRep;
	    this.detailService = detailService;
	}

	@Override
	public Ticket findByIdTicket(Long idTicket) {
		if(null == idTicket) {
			return null;
		}
		return ticketRep.findByIdTicket(idTicket);
	}

	@Override
	public List<Ticket> findByIdTicket(Date dateIni, Date dateEnd) {
		return ticketRep.findByBetweenDates(dateIni, dateEnd);
	}

	@Override
	@Transactional(readOnly = false)
	public MessageResponse createTicket(Ticket newTicket) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket ticketBd = findByIdTicket(newTicket.getIdTicket());

		if(null == ticketBd && this.saveTicket(msnResponse, newTicket)) {
		    msnResponse.setStatus(MessageResponse.CREATED_OK);
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.ALREADY_EXIST);
			msnResponse.setStatus(MessageResponse.ALREADY_EXIST);
		}
		return msnResponse;
	}

	@Override
	@Transactional(readOnly = false)
	public MessageResponse addDetailTicket(Long idTicket, Detail detail) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket ticketDb = findByIdTicket(idTicket);

		if(null != ticketDb) {
			detail.setTicket(ticketDb);
			msnResponse = this.addDetail(detail);
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	@Override
	public MessageResponse deleteDetailTicket(Long idTicket, Long idDetail) {
		MessageResponse msnResponse = new MessageResponse();
		Ticket ticketDb = findByIdTicket(idTicket);

		if(null != ticketDb) {
			Detail detail = this.detailService.findByIdTicketAndIdDetail(idTicket, idDetail);
			if(null != detail) {
				this.detailService.deleteDetail(idDetail);
				ticketDb.setTotalAmount(ticketDb.getTotalAmount() - detail.getAmount());
				this.saveTicket(msnResponse, ticketDb);
				msnResponse.setStatus(MessageResponse.PROCESS_OK);
			}
			else {
				msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST + " idDetail: " + idDetail);
				msnResponse.setStatus(MessageResponse.NO_EXIST);
			}
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST + " idTicket: " + idTicket);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	@Override
	@Transactional(readOnly = false)
	public MessageResponse deleteTicket(Long idTicket) {
		MessageResponse msnResponse = new MessageResponse();

		if(ticketRep.existsByIdTicket(idTicket)) {
		    try {
		        detailService.deleteDetailsByIdTicket(idTicket);
		        ticketRep.deleteById(idTicket);
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

	@Override
	public List<Ticket> findAllTickets() {
		return ticketRep.findAll();
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

			ticket.getDetails().forEach(detail -> {
				detail.setTicket(ticket);
				MessageResponse msnDetailsResponse = detailService.createDetail(detail);
				if(!msnDetailsResponse.getStatus().equals(MessageResponse.CREATED_OK)) {
					msnResponse.getInconsistencies().addAll(msnDetailsResponse.getInconsistencies());
					msnResponse.setStatus(msnDetailsResponse.getStatus());
				}
			});
			isOk = true;
		}
		catch (Exception e) {
			isOk = false;
			msnResponse.getInconsistencies().add(MessageResponse.SQL_ERROR + " "+ e.getMessage());
			msnResponse.setStatus(MessageResponse.SQL_ERROR);
		}
		return isOk;
	}

	/**
	 * Method adding a new detail to existing ticket and catch the inconsistencies if existing.
	 * 
	 * @param detail Ticket to save.
	 * @return Message response whit OK or bat status. 
	 */
	private MessageResponse addDetail(Detail detail) {
		MessageResponse msnResponse = new MessageResponse();
		try {
			this.detailService.createDetail(detail);
			Ticket ticket = detail.getTicket();
			ticket.setTotalAmount(ticket.getTotalAmount() + detail.getAmount());
			this.saveTicket(msnResponse, ticket);
			msnResponse.setStatus(MessageResponse.CREATED_OK);
		}
		catch (Exception e) {
			msnResponse.getInconsistencies().add(MessageResponse.SQL_ERROR + " "+ e.getMessage());
			msnResponse.setStatus(MessageResponse.SQL_ERROR);
		}
		return msnResponse;
	}

}
