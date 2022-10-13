package fr.teamunc.libcosmetics.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                case "cosmetic": {
                    if(args.length > 1) {
                        switch (args[1]) {
                            case "equip":
                                if(args.length < 3) {
                                    Message.Get().sendMessage("usage : /uncitem cosmetic equip <playerName> <customItemName>", sender, true);
                                    return true;
                                } else {
                                    Player player = Bukkit.getPlayer(args[1]);

                                    if (player == null) {
                                        Message.Get().sendMessage("Player " + args[1] + " not found!", sender, true);
                                        return true;
                                    }
                                    try {
                                        CosmeticsLib.getUncCosmeticsController().equipCosmetic(args[2], player);
                                        Message.Get().sendMessage("Cosmetic " + args[2] + " equiped to " + args[1] + "!", sender, false);
                                        return true;
                                    } catch (Exception e) {
                                        Message.Get().sendMessage("Cosmetic " + args[2] + " not found!", sender, true);
                                        return true;
                                    }
                                }
                            case "remove":
                                break;
                        }
                    }
                }
                default:
                    Message.Get().sendMessage("Unknown command!", sender, true);
                    break;
            }
        }
        return false;
    }
}
