package com.goldenrace.tickets.controllers;

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
import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.services.IDetailService;
import com.goldenrace.tickets.utils.ErrorMessage;
import com.goldenrace.tickets.utils.MessageResponse;

@Controller
@RequestMapping(value = "/ticket-details")
public class DetailController {

	@Autowired
	private IDetailService detailService;

	@GetMapping(value = "/idDetail")
    public ResponseEntity<Detail> findDetailById(@PathVariable(name="idDetail", required = true) Long idDetail){
		Detail detail = detailService.findByIdDetail(idDetail);
         if(null == detail) {
         	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Detail with id = "+idDetail+" not found.");
         }
         return ResponseEntity.ok(detail); 
     }

	@PostMapping
    public ResponseEntity<MessageResponse> createDetail(@Valid @RequestBody DetailDto detail, BindingResult result){
        if(result.hasErrors()) {
        	ErrorMessage msnError = new ErrorMessage(ErrorMessage.NEW);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MessageResponse msnResponse = detailService.createDetail(detail.getDetail());
        return ResponseEntity.status(msnResponse.generarEstadoHttp()).body(msnResponse);
    }

	@PutMapping
    public ResponseEntity<MessageResponse> updateDetail(@Valid @RequestBody DetailDto detail, BindingResult result){
        if(result.hasErrors()) {
        	ErrorMessage msnError = new ErrorMessage(ErrorMessage.NEW);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msnError.getMensaje(result));
        }
        MessageResponse msnResponse = detailService.updateDetail(detail.getDetail());
        return ResponseEntity.status(msnResponse.generarEstadoHttp()).body(msnResponse);
    }

	@DeleteMapping("/{idDetail}")
    public ResponseEntity<MessageResponse> deleteDetail(@PathVariable("idDetail") Long idDetail, BindingResult result){
        MessageResponse msnResponse = detailService.deleteDetail(idDetail);
        return ResponseEntity.status(msnResponse.generarEstadoHttp()).body(msnResponse);
    }

}
