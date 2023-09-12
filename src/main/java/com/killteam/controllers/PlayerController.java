package com.killteam.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.killteam.repository.PlayerRepository;
import com.killteam.util.PlayerModelAssembler;
import com.killteam.exceptions.PlayerNotFoundException;
import com.killteam.objects.Player;

@RestController
public class PlayerController {
    private final PlayerRepository repository;

    private final PlayerModelAssembler assembler;

    PlayerController(PlayerRepository repository, PlayerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/players")
    public
    CollectionModel<EntityModel<Player>> all() {

        List<EntityModel<Player>> players = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(players,
            linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }

    @GetMapping("/players/{id}")
    public
    EntityModel<Player> one(@PathVariable Long id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        return assembler.toModel(player);
    }  

    @PostMapping("/players")
    ResponseEntity<?> newPlayer(@RequestBody Player newPlayer) {
        EntityModel<Player> entityModel = assembler.toModel(repository.save(newPlayer));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PutMapping("/players/{id}")
    ResponseEntity<?> replacePlayer(@RequestBody Player newPlayer, @PathVariable Long id) {
        Player updatedPlayer = repository.findById(id)
            .map(player -> {
                player.setName(newPlayer.getName());
                player.setTeam(newPlayer.getTeam());
                return repository.save(player);
            })
            .orElseGet(() -> {
                newPlayer.setId(id);
                return repository.save(newPlayer);
            });

        EntityModel<Player> entityModel = assembler.toModel(updatedPlayer);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @DeleteMapping("/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}