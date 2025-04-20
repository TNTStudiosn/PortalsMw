package com.TNTStudios.portalsmw.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"))
    private void onAddMessage(Text message, MessageSignatureData signature, net.minecraft.client.gui.hud.MessageIndicator indicator, CallbackInfo ci) {
        if (isIncomingWhisper(message)) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null && client.world != null) {
                // Reproducir el sonido de notificación
                client.player.playSound(
                        SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, // Sonido mágico y distintivo
                        SoundCategory.MASTER,                    // Categoría de sonido
                        1.5F,                                    // Volumen
                        1.0F                                     // Tono
                );
            }
        }
    }

    /**
     * Verifica si un mensaje de texto corresponde a un susurro recibido.
     * Busca el patrón de traducción 'commands.message.display.incoming'.
     *
     * @param message El mensaje a verificar.
     * @return true si es un susurro entrante, false en caso contrario.
     */
    private boolean isIncomingWhisper(Text message) {
        if (message.getContent() instanceof TranslatableTextContent translatableContent) {
            if ("commands.message.display.incoming".equals(translatableContent.getKey())) {
                return true;
            }
        }

        for (Text sibling : message.getSiblings()) {
            if (isIncomingWhisper(sibling)) {
                return true;
            }
        }

        return false;
    }
}