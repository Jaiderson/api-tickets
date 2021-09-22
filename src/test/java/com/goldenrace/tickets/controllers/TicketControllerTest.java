package com.goldenrace.tickets.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldenrace.tickets.dto.DetailDto;
import com.goldenrace.tickets.dto.TicketDto;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.services.ITicketService;
import com.goldenrace.tickets.services.TestProvider;
import com.goldenrace.tickets.utils.MessageResponse;
import com.goldenrace.tickets.utils.Util;
import com.goldenrace.tickets.utils.Wraper;
import com.google.common.collect.Lists;

@WithMockUser(username = "admin")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITicketService ticketService;

    private ObjectMapper objectMapper;  

    @BeforeEach
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void findTicketByIdTest() throws Exception {
        Ticket ticket = TestProvider.getTicktetOne();
        ticket.setIdTicket(1L);

        Mockito.when(ticketService.findByIdTicket(1L)).thenReturn(ticket);

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.idTicket").value(1L))
               .andExpect(status().isOk());
    }

    @Test
    void findTicketsBetweenDatesTest() throws Exception {
        List<Ticket> tickets = TestProvider.getTicktets();
        String iniDate = "2021-09-01";
        String endDate = "2021-09-30";
        Wraper wraperIni =  Util.stringToDate(iniDate);
        Wraper wraperEnd =  Util.stringToDate(endDate);

        Mockito.when(ticketService.findByIdTicket((Date) wraperIni.getValue(), (Date) wraperEnd.getValue())).thenReturn(tickets);

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/id/"+iniDate+"/ed/"+endDate))
               .andExpect(status().isOk());
    }

    @Test
    void findTicketsBetweenDatesNotFoundTest() throws Exception {
        List<Ticket> tickets = Lists.newArrayList();
        String iniDate = "2021-10-01";
        String endDate = "2021-10-31";
        Wraper wraperIni =  Util.stringToDate(iniDate);
        Wraper wraperEnd =  Util.stringToDate(endDate);

        Mockito.when(ticketService.findByIdTicket((Date) wraperIni.getValue(), (Date) wraperEnd.getValue())).thenReturn(tickets);

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/id/"+iniDate+"/ed/"+endDate))
               .andExpect(status().isNotFound());
    }

    @Test
    void createTicketTest() throws Exception {
        TicketDto ticketDto = TestProvider.getTicktetDto();

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.CREATED_OK);
        Mockito.when(ticketService.createTicket(ticketDto.getTicket())).thenReturn(msnResponse);

        String json = objectMapper.writeValueAsString(ticketDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(MessageResponse.CREATED_OK))
                .andExpect(status().isCreated());
    }

    @Test
    void createTicketBadRequestTest() throws Exception {
        TicketDto ticketDto = TestProvider.getTicktetDto();
        ticketDto.setTotal(78000.0);

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.CREATED_OK);
        Mockito.when(ticketService.createTicket(ticketDto.getTicket())).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets")
                .content(objectMapper.writeValueAsString(ticketDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addDetailTicketTest() throws Exception {
        long idTicket = 10;
        TicketDto ticketDto = TestProvider.getTicktetDto();
        ticketDto.setTicketId(idTicket);

        DetailDto detailDto = TestProvider.getBetFootballDetailDto();

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.CREATED_OK);
        Mockito.when(ticketService.addDetailTicket(idTicket, detailDto.getDetail())).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/" + idTicket)
                .content(objectMapper.writeValueAsString(detailDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addDetailTicketBadRequestTest() throws Exception {
        long idTicket = 10;
        TicketDto ticketDto = TestProvider.getTicktetDto();
        ticketDto.setTicketId(idTicket);

        DetailDto detailDto = TestProvider.getBetFootballDetailDto();
        detailDto.setDetailAmount(-15000.0);

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.CREATED_OK);
        Mockito.when(ticketService.addDetailTicket(idTicket, detailDto.getDetail())).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/tickets/" + idTicket)
                .content(objectMapper.writeValueAsString(detailDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDetailTicketTest() throws Exception {
        long idTicket = 11;
        long idDetail = 100;

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.PROCESS_OK);
        Mockito.when(ticketService.deleteDetailTicket(idTicket, idDetail)).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/it/"+idTicket+"/id/"+idDetail))
               .andExpect(status().isOk());
    }

    @Test
    void deleteDetailTicketBadRequestTest() throws Exception {
        long idTicket = 12;
        long idDetail = 200;

        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.PROCESS_OK);
        Mockito.when(ticketService.deleteDetailTicket(idTicket, idDetail)).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/it/"+idTicket+"/id/"))
               .andExpect(status().isNotFound());
    }

    @Test
    void deleteTicketTest() throws Exception {
        long idTicket = 115;
        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.PROCESS_OK);
        Mockito.when(ticketService.deleteTicket(idTicket)).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/"+idTicket))
               .andExpect(status().isOk());
    }

    @Test
    void deleteTicketNotExistTest() throws Exception {
        long idTicket = 105;
        MessageResponse msnResponse = new MessageResponse();
        msnResponse.setStatus(MessageResponse.NO_EXIST);
        Mockito.when(ticketService.deleteTicket(idTicket)).thenReturn(msnResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tickets/"+idTicket))
               .andExpect(status().isNotFound());
    }

}
