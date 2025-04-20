package com.TNTStudios.portalsmw.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.PlayerEntity;

public class CommandExecutor {

    /**
     * Ejecuta un comando como si lo hiciera el jugador, con permisos de OP.
     *
     * @param server  El servidor de Minecraft.
     * @param player  El jugador que actuará como fuente.
     * @param command El comando a ejecutar.
     */
    public static void executeAsPlayer(MinecraftServer server, PlayerEntity player, String command) {
        ServerCommandSource source = new ServerCommandSource(
                player,
                Vec3d.ofCenter(player.getBlockPos()), // Posición
                Vec2f.ZERO,                            // Rotación
                server.getWorld(player.getWorld().getRegistryKey()), // Mundo
                4,                                     // Nivel de permiso (4 = OP)
                player.getName().getString(),          // Nombre del jugador
                Text.literal(player.getName().getString()),
                server,
                player
        );

        try {
            server.getCommandManager().getDispatcher().execute(command, source);
        } catch (CommandSyntaxException e) {
            player.sendMessage(Text.literal("§cError al ejecutar comando del portal."), false);
        }
    }
}
