package com.goldenrace.tickets.controllers;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.services.ITicketService;
import com.goldenrace.tickets.services.TestProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;  
    
    @MockBean
    private ITicketService ticketService;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "admin")
    void listarTickesTest() throws Exception {
        Ticket ticket = TestProvider.getTicktet();
        ticket.setIdTicket(1L);

        Mockito.when(ticketService.findByIdTicket(1L)).thenReturn(ticket);

        ResponseEntity<Ticket> result = template.withBasicAuth("admin", "1234")
                .getForEntity("/tickets-api/v1/tickets/1", Ticket.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
