package com.killteam.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.killteam.exceptions.TeamNotFoundException;
import com.killteam.objects.Team;
import com.killteam.repository.TeamRepository;
import com.killteam.util.TeamModelAssembler;

@RestController
public class TeamController {
    private final TeamRepository repository;
    private final TeamModelAssembler assembler;

    TeamController(TeamRepository repository, TeamModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/teams")
    public CollectionModel<EntityModel<Team>> all() {

        List<EntityModel<Team>> teams = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(teams,
            linkTo(methodOn(TeamController.class).all()).withSelfRel());
    }

    @GetMapping("/teams/{id}")
    public
    EntityModel<Team> one(@PathVariable Long id) {
        Team team = repository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return assembler.toModel(team);
    }

    @PostMapping("/teams")
    ResponseEntity<EntityModel<Team>> newTeam(@RequestBody Team team) {
        team = repository.save(team);

        return ResponseEntity
            .created(linkTo(methodOn(TeamController.class).one(team.getId())).toUri())
            .body(assembler.toModel(team));
    }

}
