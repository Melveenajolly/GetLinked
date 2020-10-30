package com.springboot.getlinked.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.getlinked.model.Professional;


@Repository
public interface ProfessionalDao extends JpaRepository<Professional, Long> {
	Professional findByEmail(String email);
	List<Professional> findByCategoryidAndVerified(int categoryid, int verified);
	List<Professional> findByVerified(int verified);

	@Modifying
	@Transactional
	@Query("Update Professional professional set professional.verified=?1 where professional.id=?2")
	void setProfessinalVerified( int verified,Long professionalid );
}




