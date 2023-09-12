package com.killteam.util;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.killteam.controllers.PlayerController;
import com.killteam.objects.Player;

@Component
public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>> {
    
    @Override
    public EntityModel<Player> toModel(Player player) {
        return EntityModel.of(player,
        
            linkTo(methodOn(PlayerController.class).one(player.getId())).withSelfRel(),
            linkTo(methodOn(PlayerController.class).all()).withRel("players"));
    }
}
