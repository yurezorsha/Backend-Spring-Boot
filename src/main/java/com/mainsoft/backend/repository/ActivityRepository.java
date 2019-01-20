package com.mainsoft.backend.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mainsoft.backend.entity.Activity;
import com.mainsoft.backend.entity.Report;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	@Query("SELECT a FROM Activity  a where a.user.username = :username")
	List<Activity> findAllByUserName(@Param("username") String username);

}
