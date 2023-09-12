package com.killteam.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.killteam.controllers.TeamController;
import com.killteam.objects.Team;

@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, EntityModel<Team>> {

    @Override
    public EntityModel<Team> toModel(Team team) {
        final EntityModel<Team> teamModel = EntityModel.of(team,
            linkTo(methodOn(TeamController.class).one(team.getId())).withSelfRel(),
            linkTo(methodOn(TeamController.class).all()).withRel("teams"));
        
        return teamModel;
    }
    
}
