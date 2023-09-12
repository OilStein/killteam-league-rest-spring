package com.killteam.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.killteam.objects.Player;
import com.killteam.objects.Team;
import com.killteam.repository.PlayerRepository;
import com.killteam.repository.TeamRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadFile {

    @Autowired
    private final PlayerRepository playerRepository;
    @Autowired
    private final TeamRepository teamRepository;

    public void loadData() {
        loadTeams();
        loadPlayers();
    }

    void loadPlayers() {
        List<Player> players = readPlayers();
        playerRepository.saveAll(players);
    }

    void loadTeams() {
        List<Team> teams = readTeams();
        teamRepository.saveAll(teams);
    }


    List<Player> readPlayers() {

        Path path = Paths.get("src/main/resources/data/players.csv");

        List<Player> players = List.of();
      
        try {
            int initialCapacity = (int) Files.lines(path).count();
            players = new ArrayList<>(initialCapacity);

            BufferedReader reader = Files.newBufferedReader(path);
            reader.readLine(); // skip header
            while (reader.ready()) {
                String line = reader.readLine();
                Player player = parsePlayer(line);
                players.add(player);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    List<Team> readTeams() {

        Path path = Paths.get("src/main/resources/data/teams.csv");

        List<Team> teams = List.of();

        try {
            int initialCapacity = (int) Files.lines(path).count();
            teams = new ArrayList<>(initialCapacity);

            BufferedReader reader = Files.newBufferedReader(path);
            reader.readLine(); // skip header
            while (reader.ready()) {
                String line = reader.readLine();
                Team team = parseTeam(line);
                teams.add(team);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teams;
    }

    // TODO: make validation for parsePlayer and parseTeam

    Player parsePlayer(String line) {
        String[] tokens = line.split(";");
        Team team = teamRepository.findById(Long.parseLong(tokens[2])).orElse(null);
        Player player = new Player(Long.parseLong(tokens[0]),tokens[1], team);
        return player;
    }

    Team parseTeam(String line) {
        String[] tokens = line.split(";");
        Team team = new Team(Long.parseLong(tokens[0]),tokens[1]);
        return team;
    }

}
