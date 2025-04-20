package com.TNTStudios.portalsmw.block;

import com.TNTStudios.portalsmw.portal.PortalManager;
import com.TNTStudios.portalsmw.util.CommandExecutor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class TnzPortalBlock extends Block {

    public TnzPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isClient) return;

        for (int i = 0; i < 4; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 0.5 + random.nextDouble();
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            Vector3f color = new Vector3f(0.2f, 0.4f, 1.0f); // Azul

            world.addParticle(
                    new DustParticleEffect(color, 1.0f),
                    x, y, z,
                    0.0, 0.01, 0.0
            );
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !(entity instanceof PlayerEntity player)) return;

        ServerWorld serverWorld = (ServerWorld) world;

        if (!PortalManager.isPortalActive()) {
            player.sendMessage(Text.literal("§cEl portal está desactivado, vuelve más tarde..."), true);
            player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS.value(), SoundCategory.PLAYERS, 1f, 1f);
            return;
        }

        String command = PortalManager.getCommandIfActive();
        if (command != null && !command.isBlank()) {
            CommandExecutor.executeAsPlayer(serverWorld.getServer(), player, command);
        }
    }
}
