package com.TNTStudios.portalsmw.registry;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.TNTStudios.portalsmw.portal.PortalManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(createPortalCommand());
        });
    }

    private static LiteralArgumentBuilder<ServerCommandSource> createPortalCommand() {
        return CommandManager.literal("portal")
                .requires(source -> source.hasPermissionLevel(4)) // Solo OPs
                .then(CommandManager.literal("create")
                        .then(CommandManager.argument("name", StringArgumentType.word())
                                .then(CommandManager.argument("command", StringArgumentType.greedyString())
                                        .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                                .executes(ctx -> {
                                                    String name = StringArgumentType.getString(ctx, "name");
                                                    String command = StringArgumentType.getString(ctx, "command");
                                                    BlockPos pos = BlockPosArgumentType.getBlockPos(ctx, "pos");
                                                    PortalManager.createPortal(name, command, pos);
                                                    ctx.getSource().sendFeedback(() -> Text.literal("§aPortal '" + name + "' creado en " + pos.toShortString()), true);
                                                    return 1;
                                                })))))
                .then(CommandManager.literal("activar")
                        .then(CommandManager.argument("name", StringArgumentType.word())
                                .executes(ctx -> {
                                    String name = StringArgumentType.getString(ctx, "name");
                                    PortalManager.activatePortal(name);
                                    ctx.getSource().sendFeedback(() -> Text.literal("§aPortal '" + name + "' activado."), true);
                                    return 1;
                                })))
                .then(CommandManager.literal("desactivado")
                        .then(CommandManager.argument("name", StringArgumentType.word())
                                .executes(ctx -> {
                                    String name = StringArgumentType.getString(ctx, "name");
                                    PortalManager.deactivatePortal(name);
                                    ctx.getSource().sendFeedback(() -> Text.literal("§cPortal '" + name + "' desactivado."), true);
                                    return 1;
                                })))
                .then(CommandManager.literal("reload")
                        .executes(ctx -> {
                            PortalManager.loadPortals();
                            ctx.getSource().sendFeedback(() -> Text.literal("§ePortales recargados desde el archivo JSON."), true);
                            return 1;
                        }));
    }
}
