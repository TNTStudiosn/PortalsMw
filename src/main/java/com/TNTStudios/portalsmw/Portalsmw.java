package com.TNTStudios.portalsmw;

import com.TNTStudios.portalsmw.portal.PortalManager;
import com.TNTStudios.portalsmw.registry.ModBlocks;
import com.TNTStudios.portalsmw.registry.ModCommands;
import net.fabricmc.api.ModInitializer;

public class Portalsmw implements ModInitializer {

    @Override
    public void onInitialize() {
        ModBlocks.register();
        PortalManager.loadPortals();
        ModCommands.register();
    }

}
