package me.lyric.infinity.impl.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.lyric.infinity.Infinity;
import me.lyric.infinity.api.command.Command;
import me.lyric.infinity.api.command.CommandState;
import me.lyric.infinity.api.util.minecraft.chat.ChatUtils;
import me.lyric.infinity.manager.Managers;
import me.lyric.infinity.manager.client.CommandManager;

/**
 * @author lyric
 */

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix", "Change the command prefix.");
    }

    @Override
    public String theCommand() {
        return "prefix <char>";
    }

    @Override
    public void onCommand(String[] args) {
        String character = null;

        if (args.length > 1) {
            character = args[1];
        }

        if (character == null || args.length > 2) {
            this.splash(CommandState.ERROR);

            return;
        }

        CommandManager.setPrefix(character);

        ChatUtils.sendMessage("Prefix changed to " + ChatFormatting.WHITE + character);

        this.splash(CommandState.PERFORMED);
    }
}