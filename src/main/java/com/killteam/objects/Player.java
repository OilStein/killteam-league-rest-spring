package com.killteam.objects;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private @Id @GeneratedValue Long id;
    private String name;

    @ManyToOne
    private Team team;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Player(String name) {
        this.name = name;
    }
}
