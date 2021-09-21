package com.goldenrace.tickets.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.repositories.IDetailRep;
import com.goldenrace.tickets.services.IDetailService;
import com.goldenrace.tickets.utils.MessageResponse;

@Service
public class DetailServiceImpl implements IDetailService {

    @Autowired
    private IDetailRep detailRep;

    public DetailServiceImpl(IDetailRep detailRep) {
        super();
        this.detailRep = detailRep;
    }

	@Override
	public Detail findByIdDetail(Long idDetail) {
		return detailRep.findByIdDetail(idDetail);
	}

	@Override
	public List<Detail> findByIdTicket(Long idTicket) {
		return detailRep.findByIdTicket(idTicket);
	}

	@Override
	public MessageResponse createDetail(Detail newDetail) {
		MessageResponse msnResponse = new MessageResponse();
	    if(this.saveDetail(msnResponse, newDetail) ) {
		   msnResponse.setStatus(MessageResponse.CREATED_OK);
	    }
		return msnResponse;
	}

	@Override
	public MessageResponse updateDetail(Detail detail) {
		MessageResponse msnResponse = new MessageResponse();
		Detail detailDb = findByIdDetail(detail.getIdDetail());

		if(null != detailDb && this.saveDetail(msnResponse, detail)) {
	        msnResponse.setStatus(MessageResponse.PROCESS_OK);
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	@Override
	public MessageResponse deleteDetail(Long idDetail) {
		MessageResponse msnResponse = new MessageResponse();
		Detail detailDb = findByIdDetail(idDetail);

		if(null != detailDb) {
			try {
				detailRep.delete(detailDb);
				msnResponse.setStatus(MessageResponse.PROCESS_OK);
			}
			catch (Exception e) {
				msnResponse.setStatus(MessageResponse.SQL_ERROR + " " + e.getMessage());
			}
		}
		else {
			msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
			msnResponse.setStatus(MessageResponse.NO_EXIST);
		}
		return msnResponse;
	}

	/**
	 * Method saved a detail and catch the inconsistencies if existing.
	 * 
	 * @param msnResponse Object with the inconsistencies if existing.
	 * @param detail Detail to save.
	 */
	private boolean saveDetail(MessageResponse msnResponse, Detail detail) {
	    boolean isOk = false;
		try {
			detailRep.save(detail);
			isOk = true;
		}
		catch (Exception e) {
			isOk = false;
			msnResponse.getInconsistencies().add(MessageResponse.SQL_ERROR + " "+ e.getMessage());
			msnResponse.setStatus(MessageResponse.SQL_ERROR);
		}
		return isOk;
	}

    @Override
    public MessageResponse deleteDetailsByIdTicket(Ticket ticket) {
        MessageResponse msnResponse = new MessageResponse();

        if(null != ticket) {
            try {
                detailRep.deleteByIdTicket(ticket.getIdTicket());
                msnResponse.setStatus(MessageResponse.PROCESS_OK);
            }
            catch (Exception e) {
                msnResponse.setStatus(MessageResponse.SQL_ERROR + " " + e.getMessage());
            }
        }
        else {
            msnResponse.getInconsistencies().add(MessageResponse.NO_EXIST);
            msnResponse.setStatus(MessageResponse.NO_EXIST);
        }
        return msnResponse;
    }

}
