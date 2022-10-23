package fr.teamunc.libcosmetics.minecraft.commandsExecutor;

import fr.teamunc.base_unclib.models.libtools.CommandsTab;
import fr.teamunc.base_unclib.utils.helpers.Message;
import fr.teamunc.libcosmetics.CosmeticsLib;
import fr.teamunc.libcosmetics.models.cosmetic.Cosmetic;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CosmeticsCommands extends CommandsTab implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!CosmeticsLib.isInit()) {
            Message.Get().sendMessage("CustomItemLib is not initialized!", sender, true);
            return true;
        }
        if(args.length != 0) {
            if(args[0].equalsIgnoreCase("cosmetic")) {
                Bukkit.getServer().broadcastMessage("ici");
                if(args.length > 1) {
                    switch (args[1]) {
                        case "equip":
                            Message.Get().sendMessage("on est ici", sender, true);
                            if(args.length < 3) {
                                Message.Get().sendMessage("usage : /cosmetic equip <playerName> <customItemName>", sender, true);
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
                        case "add":
                            break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> result = checkAllTab(
                args,
                Arrays.asList("add"),
                CosmeticsLib.getPlugin().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()),
                new ArrayList<>(CosmeticsLib.getUncCosmeticsController().getRegisteredCosmetics().stream()
                        .map(Cosmetic::getCosmeticKey)
                        .collect(Collectors.toList())
                ));

        //sort the list
        Collections.sort(result);

        return result;
    }
}
