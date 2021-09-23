package com.goldenrace.tickets.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goldenrace.tickets.entities.Detail;

@Repository
public interface IDetailRep extends JpaRepository<Detail, Long> {

	@Query(value="Select D.* From details D Where D.ticket_id = :idTicket ", nativeQuery = true)
	public List<Detail> findByIdTicket(@Param("idTicket") Long idTicket);

	@Modifying
	@Query(value="Delete From details Where ticket_id = :idTicket ", nativeQuery = true)
	public void deleteByIdTicket(@Param("idTicket") Long idTicket);

	@Query(value="Select * From details Where ticket_id = :idTicket and detail_id = :idDetail ", nativeQuery = true)
	public Detail findByIdDetailAndIdTicket(@Param("idTicket") Long idTicket,
			                                @Param("idDetail") Long idDetail);

	public Detail findByIdDetail(Long idDetail);

}
