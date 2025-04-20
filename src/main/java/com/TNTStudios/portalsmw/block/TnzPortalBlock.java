package com.TNTStudios.portalsmw.block;

import com.TNTStudios.portalsmw.portal.PortalManager;
import com.TNTStudios.portalsmw.util.CommandExecutor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TnzPortalBlock extends Block {

    public TnzPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.isClient || !(entity instanceof PlayerEntity player)) return;

        ServerWorld serverWorld = (ServerWorld) world;
        String portalKey = PortalManager.getKeyFromPos(pos);

        if (portalKey == null) return;

        if (!PortalManager.isPortalActive(portalKey)) {
            player.sendMessage(Text.literal("§cEl portal está desactivado, vuelve más tarde..."), true);
            player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.PLAYERS, 1f, 1f);
            return;
        }

        String command = PortalManager.getCommandForPortal(portalKey);
        if (command != null && !command.isBlank()) {
            CommandExecutor.executeAsPlayer(serverWorld.getServer(), player, command);
        }
    }
}
