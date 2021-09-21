package com.goldenrace.tickets.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.goldenrace.tickets.repositories.IDetailRep;
import com.goldenrace.tickets.repositories.ITicketRep;
import com.goldenrace.tickets.services.implementations.DetailServiceImpl;
import com.goldenrace.tickets.services.implementations.TicketServiceImpl;
import com.goldenrace.tickets.utils.MessageResponse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TicketServiceTest {

    @Autowired
    private IDetailRep detailRep;
    @Autowired
    private ITicketRep ticketRep;

    private IDetailService detailService;
    private ITicketService ticketService;

    private Ticket ticket;

    @BeforeEach
    public void init() {
        detailService = new DetailServiceImpl(detailRep);
        ticketService = new TicketServiceImpl(ticketRep, detailService);
    }

    @Test
    void createTicketTest() {
        this.ticket = TestProvider.getTicktet();
        MessageResponse msnResponse = this.ticketService.createTicket(ticket);
        assertThat(this.ticket.getIdTicket()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);
    }

    @Test
    void addDetailTicketTest() {
        this.ticket = TestProvider.getTicktet();
        MessageResponse msnResponse = this.ticketService.createTicket(ticket);
        assertThat(this.ticket.getIdTicket()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        Detail detail = TestProvider.getBetBaseballDetail();
        double total = this.ticket.getTotalAmount() + detail.getAmount();
        msnResponse = this.ticketService.addDetailTicket(this.ticket.getIdTicket(), detail);

        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);
        assertThat(detail.getIdDetail()).isPositive();
        assertThat(this.ticket.getTotalAmount()).isEqualTo(total);
    }

    @Test
    void deleteDetailTicketTest() {
        this.ticket = TestProvider.getTicktet();
        MessageResponse msnResponse = this.ticketService.createTicket(ticket);
        assertThat(this.ticket.getIdTicket()).isPositive();
        assertThat(this.ticket.getDetails().size()).isGreaterThan(0);
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        Detail detail = this.ticket.getDetails().get(0);
        Long idDetail = detail.getIdDetail();
        msnResponse = this.ticketService.deleteDetailTicket(this.ticket.getIdTicket(), idDetail);

        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.PROCESS_OK);
        detail = this.detailService.findByIdDetail(idDetail);
        assertThat(detail).isNull();
    }

    @Test
    void deleteTicketTest() {
        this.ticket = TestProvider.getTicktet();
        MessageResponse msnResponse = this.ticketService.createTicket(ticket);
        assertThat(this.ticket.getIdTicket()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        msnResponse = this.ticketService.deleteTicket(this.ticket.getIdTicket());
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.PROCESS_OK);
        this.ticket = this.ticketService.findByIdTicket(this.ticket.getIdTicket());
        assertThat(this.ticket).isNull();
    }

}
