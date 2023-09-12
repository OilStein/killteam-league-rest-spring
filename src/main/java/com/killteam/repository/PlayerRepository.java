package com.killteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.killteam.objects.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}

