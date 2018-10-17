package com.fleventyfive.CRUDDemo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Player {
    @Id @GeneratedValue private Long id;

    private String username;
    private int level;

    protected Player() { }

    public Player(String username, int level) {
        this.username = username;
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("Player[id=%d, username=%s, description=%s]", id, username, level);
    }
}
