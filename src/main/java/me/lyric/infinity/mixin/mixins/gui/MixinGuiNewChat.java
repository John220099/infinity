package me.lyric.infinity.mixin.mixins.gui;

import me.lyric.infinity.impl.modules.misc.BetterChat;
import me.lyric.infinity.manager.Managers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(value = GuiNewChat.class)
public abstract class MixinGuiNewChat extends MixinGui {

    @Final
    @Shadow
    public final Minecraft mc = Minecraft.getMinecraft();

    @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawChatHook1(int left, int top, int right, int bottom, int color) {
        if (Managers.MODULES.getModuleByClass(BetterChat.class).isEnabled() && Managers.MODULES.getModuleByClass(BetterChat.class).rect.getValue()) {
            Gui.drawRect(left, top, right, bottom, 0);
        } else {
            Gui.drawRect(left, top, right, bottom, color);
        }

    }

    @Redirect(method = {"setChatLine"}, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0, remap = false))
    public int drawnChatLinesSize(List<ChatLine> list) {
        return Managers.MODULES.getModuleByClass(BetterChat.class).isEnabled() && Managers.MODULES.getModuleByClass(BetterChat.class).inf.getValue() ? -2147483647 : list.size();
    }

    @Redirect(method = {"setChatLine"}, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2, remap = false))
    public int chatLinesSize(List<ChatLine> list) {
        return Managers.MODULES.getModuleByClass(BetterChat.class).isEnabled() && Managers.MODULES.getModuleByClass(BetterChat.class).inf.getValue() ? -2147483647 : list.size();
    }
}