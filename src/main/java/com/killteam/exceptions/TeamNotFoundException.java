package com.killteam.exceptions;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(Long id) {
        super("Could not find team " + id);
    }
    
}
