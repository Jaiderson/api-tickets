package com.goldenrace.tickets.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.goldenrace.tickets.dto.DetailDto;
import com.goldenrace.tickets.dto.TicketDto;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.services.ITicketService;
import com.goldenrace.tickets.utils.ErrorMessage;
import com.goldenrace.tickets.utils.MessageResponse;
import com.goldenrace.tickets.utils.Util;
import com.goldenrace.tickets.utils.Wraper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/tickets")
public class TicketController {

	@Autowired
	private ITicketService ticketService;

	//TODO Delete this method
	@GetMapping
    public ResponseEntity<List<Ticket>> findAllTickets(){
		List<Ticket> ticket = ticketService.findAllTickets();
         if(ticket.isEmpty()) {
         	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets not found.");
         }
         return ResponseEntity.ok(ticket); 
     }

	@GetMapping(value = "/{idTicket}")
    @ApiOperation(value = "Allows you to search for a betting ticket given its id.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Search ends successfully."),
        @ApiResponse(code = 204, message = "No content."),
        @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
        @ApiResponse(code = 404, message = "No records were found.")
    })
    public ResponseEntity<Ticket> findTicketById(@PathVariable(name="idTicket", required = true) Long idTicket){
		Ticket ticket = ticketService.findByIdTicket(idTicket);
         if(null == ticket) {
         	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket with id = "+idTicket+" not found.");
         }
         return ResponseEntity.ok(ticket); 
     }

	@GetMapping(value = "/id/{iniDate}/ed/{endDate}")
	@ApiOperation(value = "Allows you to search for a betting ticket in a range of dates.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Search ends successfully."),
        @ApiResponse(code = 204, message = "No content."),
        @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
        @ApiResponse(code = 400, message = "Bad request."),
        @ApiResponse(code = 404, message = "No records were found.")
    })
    public ResponseEntity<List<Ticket>> findTicketsBetweenDates(
    		@PathVariable(name="iniDate", required = true) String iniDate,
    		@PathVariable(name="endDate", required = true) String endDate){
		Wraper wraperIni =	Util.stringToDate(iniDate);
		Wraper wraperEnd =	Util.stringToDate(endDate);

		if(!wraperIni.isOk() || !wraperEnd.isOk()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format, please use yyyy-MM-dd");
		}
		List<Ticket> tickets = ticketService.findByIdTicket((Date) wraperIni.getValue(), (Date) wraperEnd.getValue());
		if(tickets.isEmpty()) {
		    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tickets not found.");
		}
         return ResponseEntity.ok(tickets);
     }

	@PostMapping
	@ApiOperation(value = "Allows you to create a ticket to the user with their respective details "+
	                      "or if you wish without details which can be added later.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Record successfully created."),
        @ApiResponse(code = 204, message = "No content."),
        @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
        @ApiResponse(code = 400, message = "Bad request.")
    })
    public ResponseEntity<MessageResponse> createTicket(@Valid @RequestBody TicketDto ticketDto, BindingResult result){
        if(result.hasErrors()) {
        	ErrorMessage msnError = new ErrorMessage(ErrorMessage.NEW);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        
        Ticket ticket = ticketDto.getTicket();
        if(!ticket.isOkTotalAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Total amount different to details amount sum.");
        }

        MessageResponse msnResponse = ticketService.createTicket(ticket);
        return ResponseEntity.status(msnResponse.generateHttpStatus()).body(msnResponse);
    }

	@PutMapping("/{idTicket}")
	@ApiOperation(value = "Allows you to add a wager item (detail) to an existing ticket.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Search ends successfully."),
	    @ApiResponse(code = 204, message = "No content."),
	    @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
	    @ApiResponse(code = 400, message = "Bad request."),
		@ApiResponse(code = 404, message = "Tticket does not exist.")
	})
    public ResponseEntity<MessageResponse> addDetailTicket(
    		@PathVariable(name="idTicket", required = true) Long idTicket,
    		@Valid @RequestBody DetailDto detail, BindingResult result){

		if(result.hasErrors()) {
        	ErrorMessage msnError = new ErrorMessage(ErrorMessage.NEW);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MessageResponse msnResponse = ticketService.addDetailTicket(idTicket, detail.getDetail());
        return ResponseEntity.status(msnResponse.generateHttpStatus()).body(msnResponse);
    }

	@DeleteMapping("/it/{idTicket}/id/{idDetail}")
	@ApiOperation(value = "Allows you to remove a bet item (detail) from an existing ticket.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Search ends successfully."),
	    @ApiResponse(code = 204, message = "No content."),
	    @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
	    @ApiResponse(code = 400, message = "Bad request."),
		@ApiResponse(code = 404, message = "Tticket does not exist.")
	})
    public ResponseEntity<MessageResponse> deleteDetailTicket(
    		@PathVariable(name="idTicket", required = true) Long idTicket,
    		@PathVariable(name="idDetail", required = true) Long idDetail){

        MessageResponse msnResponse = ticketService.deleteDetailTicket(idTicket, idDetail);
        return ResponseEntity.status(msnResponse.generateHttpStatus()).body(msnResponse);
    }

	@DeleteMapping("/{idTicket}")
	@ApiOperation(value = "Allows you to delete a ticket which will not only delete the "+
	                      "ticket but also its associated items (details).")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Search ends successfully."),
	    @ApiResponse(code = 204, message = "No content."),
	    @ApiResponse(code = 401, message = "Access to the unauthorized resource."),
	    @ApiResponse(code = 400, message = "Bad request."),
		@ApiResponse(code = 404, message = "Tticket does not exist.")
	})
    public ResponseEntity<MessageResponse> deleteTicket(@PathVariable("idTicket") Long idTicket){
        MessageResponse msnResponse = ticketService.deleteTicket(idTicket);
        return ResponseEntity.status(msnResponse.generateHttpStatus()).body(msnResponse);
    }

}
