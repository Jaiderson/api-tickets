package com.goldenrace.tickets.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.goldenrace.tickets.entities.Detail;

@Repository
public interface IDetailRep extends JpaRepository<Detail, Long> {

	@Query(value="Select D.* From details D Where D.ticket_id = :idTicket ", nativeQuery = true)
	public List<Detail> findByIdTicket(Long idTicket);	

	@Query(value="Delete From details Where ticket_id = :idTicket ", nativeQuery = true)
	public boolean deleteByIdTicket(Long idTicket);

	public Detail findByIdDetail(Long idTicket);

}
