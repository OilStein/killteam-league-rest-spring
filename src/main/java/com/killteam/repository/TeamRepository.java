package com.killteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.killteam.objects.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
    
}
