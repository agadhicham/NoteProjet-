package org.m2i.dao;

import org.m2i.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INoteRepository extends JpaRepository<Note, Long>{
	@Query("select n from Note n where n.noty like :x ")
	Page<Note>cherche(@Param("x")String mc,Pageable pageable);
}
