package fr.teamunc.libcosmetics.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.libcosmetics.CosmeticsLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CosmeticsCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!CosmeticsLib.isInit()) {
            Message.Get().sendMessage("CustomItemLib is not initialized!", sender, true);
            return true;
        }
        if(args.length != 0) {
            switch (args[0]) {
                case "reload":
                    CosmeticsLib.reload();
                    Message.Get().sendMessage("CustomItemLib reloaded!", sender, true);
                    break;
                default:
                    Message.Get().sendMessage("Unknown command!", sender, true);
                    break;
            }
        }
        return false;
    }
}
