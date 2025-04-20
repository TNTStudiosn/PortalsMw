package com.TNTStudios.portalsmw.portal;

import net.minecraft.util.math.BlockPos;

public class PortalData {

    private String name;
    private String command;
    private BlockPos position;
    private boolean active;

    public PortalData(String name, String command, BlockPos position, boolean active) {
        this.name = name;
        this.command = command;
        this.position = position;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public BlockPos getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
