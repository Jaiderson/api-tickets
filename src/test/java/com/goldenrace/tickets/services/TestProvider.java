package com.goldenrace.tickets.services;

import java.util.List;

import com.goldenrace.tickets.entities.Detail;
import com.goldenrace.tickets.entities.Ticket;
import com.google.common.collect.Lists;

public class TestProvider {

    public static Detail getBetHorseDetail() {
        return Detail.builder().amount(15000.0)
                               .description("Bet horse race.")
                               .build();
    }

    public static Detail getBetFootballDetail() {
        return Detail.builder().amount(10000.0)
                               .description("Bet football game.")
                               .build();
    }

    public static Detail getBetSoccerDetail() {
        return Detail.builder().amount(50000.0)
                               .description("Bet soccer game.")
                               .build();
    }

    public static Detail getBetBaseballDetail() {
        return Detail.builder().amount(5000.0)
                               .description("Bet baseball game.")
                               .build();
    }

    public static List<Detail> getDetails(){
        List<Detail> list = Lists.newArrayList();
        list.add(getBetHorseDetail());
        list.add(getBetFootballDetail());
        list.add(getBetSoccerDetail());
        return list;
    }

    public static Ticket getTicktet() {
        return Ticket.builder().totalAmount(75000.0)
                               .details(getDetails())
                               .build();
    }

}
