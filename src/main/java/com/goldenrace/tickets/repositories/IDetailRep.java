package com.goldenrace.tickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenrace.tickets.entities.Detail;

@Repository
public interface IDetailRep extends JpaRepository<Detail, Long> {

}
