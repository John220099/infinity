package me.lyric.infinity.impl.modules.misc;

import me.lyric.infinity.api.event.events.blocks.CanCollideCheckEvent;
import me.lyric.infinity.api.module.Category;
import me.lyric.infinity.api.module.Module;
import event.bus.EventListener;

/**
 * @author zzurio
 */

public class LiquidInteract extends Module {

    public LiquidInteract() {
        super("LiquidInteract", "Allows you to place blocks in liquids.", Category.MISC);
    }

    @EventListener
    public void canCollide(CanCollideCheckEvent event) {
        if (!nullSafe()) return;
        event.cancel();
    }
}
