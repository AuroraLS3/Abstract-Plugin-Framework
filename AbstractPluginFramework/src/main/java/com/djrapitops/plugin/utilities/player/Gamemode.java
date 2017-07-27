/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.utilities.player;

/**
 *
 * @author Rsl1122
 */
public enum Gamemode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int number;

    Gamemode(int number) {
        this.number = number;
    }

    public static Gamemode wrap(org.bukkit.GameMode gm) {
        switch (gm) {
            case SURVIVAL:
                return SURVIVAL;
            case CREATIVE:
                return CREATIVE;
            case ADVENTURE:
                return ADVENTURE;
            case SPECTATOR:
                return SPECTATOR;
            default:
                throw new IllegalArgumentException("Argument not supported.");
        }
    }
}
