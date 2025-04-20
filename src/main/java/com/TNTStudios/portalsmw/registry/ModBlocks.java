package com.TNTStudios.portalsmw.registry;

import com.TNTStudios.portalsmw.block.TnzPortalBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final String MOD_ID = "portalsmw";

    public static final Block TNZ_PORTAL_BLOCK = new TnzPortalBlock(Block.Settings.create()
            .strength(1.0f)
            .nonOpaque()
            .noCollision()
            .luminance(state -> 12) // ← Nivel de luz (máx. 15)
    );

    public static void register() {
        Identifier id = new Identifier(MOD_ID, "tnz_portal");
        Registry.register(Registries.BLOCK, id, TNZ_PORTAL_BLOCK);
        Registry.register(Registries.ITEM, id, new BlockItem(TNZ_PORTAL_BLOCK, new FabricItemSettings()));
    }
}
