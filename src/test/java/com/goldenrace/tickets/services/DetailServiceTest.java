package com.goldenrace.tickets.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
@TestMethodOrder(OrderAnnotation.class)
public class DetailServiceTest {

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

        this.ticket = TestProvider.getTicktet();
        this.ticketService.createTicket(ticket);
    }

    @Test
    @Order(1)
    void createDetailTest() {
        Detail detail = TestProvider.getBetBaseballDetail();
        detail.setTicket(ticket);
        MessageResponse msnResponse = this.detailService.createDetail(detail);
        assertThat(detail.getIdDetail()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);
    }

    @Test
    @Order(2)
    void updateDetailTest() {
        Detail detail = TestProvider.getBetBaseballDetail();
        detail.setTicket(ticket);
        MessageResponse msnResponse = this.detailService.createDetail(detail);
        assertThat(detail.getIdDetail()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        Long idDetail = detail.getIdDetail();
        detail.setDescription("Bet rugby play");
        detail.setAmount(12500.0);
        msnResponse = this.detailService.updateDetail(detail);

        detail = this.detailService.findByIdDetail(idDetail);
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.PROCESS_OK);
        assertThat(detail.getDescription()).isEqualTo("Bet rugby play");
        assertThat(detail.getAmount()).isEqualTo(12500);
    }

    @Test
    @Order(3)
    void deleteDetailTest() {
        Detail detail = TestProvider.getBetBaseballDetail();
        detail.setTicket(ticket);
        MessageResponse msnResponse = this.detailService.createDetail(detail);
        assertThat(detail.getIdDetail()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        Long idDetail = detail.getIdDetail();
        msnResponse = this.detailService.deleteDetail(idDetail);

        detail = this.detailService.findByIdDetail(idDetail);
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.PROCESS_OK);
        assertThat(detail).isNull();
    }

    @Test
    @Order(4)
    void findDetailTest() {
        Detail detail = TestProvider.getBetBaseballDetail();
        detail.setTicket(ticket);
        MessageResponse msnResponse = this.detailService.createDetail(detail);
        assertThat(detail.getIdDetail()).isPositive();
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.CREATED_OK);

        Long idDetail = detail.getIdDetail();
        msnResponse = this.detailService.deleteDetail(idDetail);

        detail = this.detailService.findByIdDetail(idDetail);
        assertThat(msnResponse.getStatus()).isEqualTo(MessageResponse.PROCESS_OK);
        assertThat(detail).isNull();
    }

}
