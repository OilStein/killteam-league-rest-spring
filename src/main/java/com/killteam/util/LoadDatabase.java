package com.killteam.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.killteam.repository.PlayerRepository;
import com.killteam.repository.TeamRepository;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PlayerRepository repository, TeamRepository teamRepository) {
        return args -> {
            log.info("Preloading data!");
            ReadFile readFile = new ReadFile(repository, teamRepository);
            readFile.loadData();
            log.info("Finished preloading data!");
        };
    }

}
