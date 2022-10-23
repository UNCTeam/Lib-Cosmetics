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
            if(command.getName().equalsIgnoreCase("cosmetic")) {
                switch (args[0]) {
                    case "equip":
                        if(args.length < 2) {
                            Message.Get().sendMessage("usage : /cosmetic equip <CosmeticName>", sender, true);
                            return true;
                        } else {
                            try {
                                CosmeticsLib.getUncCosmeticsController().equipCosmetic(args[1], (Player) sender);
                                Message.Get().sendMessage("Cosmetic " + args[1] + " equiped !", sender, false);
                                return true;
                            } catch (Exception e) {
                                Message.Get().sendMessage("Cosmetic " + args[1] + " not found!", sender, true);
                                return true;
                            }
                        }
                    case "remove":
                        if(args.length < 2) {
                            Message.Get().sendMessage("usage : /cosmetic remove <cosmetic>", sender, true);
                            return true;
                        } else {
                            try {
                                if (CosmeticsLib.getUncCosmeticsController().getCustomCosmeticMap().containsKey(args[1])) {
                                    CosmeticsLib.getUncCosmeticsController().removeCosmetic(args[1], (Player) sender);
                                    Message.Get().sendMessage("Cosmetic " + args[1] + " removed !", sender, false);
                                } else {
                                    throw new Exception();
                                }
                                return true;
                            } catch (Exception e) {
                                Message.Get().sendMessage("Cosmetic " + args[1] + " not found!", sender, true);
                                return true;
                            }
                        }
                    case "add":
                        if(args.length < 3) {
                            Message.Get().sendMessage("usage : /cosmetic add <playerName> <customItemName>", sender, true);
                            return true;
                        } else {
                            Player player = Bukkit.getPlayer(args[1]);
                            if (player == null) {
                                Message.Get().sendMessage("Player " + args[1] + " not found!", sender, true);
                                return true;
                            }
                            try {
                                CosmeticsLib.getUncCosmeticsController().addCosmeticToPlayer(args[2], player);
                                Message.Get().sendMessage("Cosmetic " + args[2] + " added to " + args[1] + "!", sender, false);
                                return true;
                            } catch (Exception e) {
                                Message.Get().sendMessage("Cosmetic " + args[2] + " not found!", sender, true);
                                return true;
                            }
                        }
                    case "list":
                        Message.Get().sendMessage("Cosmetics list : ", sender, false);
                        Player player = (Player) sender;
                        List<String> cosmetics = CosmeticsLib.getUncCosmeticsController().getPlayerCosmeticsContainer().getOwnedPlayerCosmetics().get(player.getUniqueId());
                        if(cosmetics == null) {
                            Message.Get().sendMessage("You don't have any cosmetic!", sender, true);
                            return true;
                        } else {
                            Message.Get().sendMessage("Your cosmetics : ", sender, false);
                            for(String cosmetic : cosmetics) {
                                Message.Get().sendMessage(cosmetic, sender, false);
                            }
                            return true;
                        }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> result;
        if(args[0].equalsIgnoreCase("equip") || args[0].equalsIgnoreCase("remove")) {
            result = checkAllTab(
                    args,
                    Arrays.asList("equip", "remove", "add", "list"),
                    new ArrayList<>(CosmeticsLib.getUncCosmeticsController().getRegisteredCosmetics().stream()
                            .map(Cosmetic::getCosmeticKey)
                            .collect(Collectors.toList())
                    ),
                    Arrays.asList(""));
        } else {
            result = checkAllTab(
                    args,
                    Arrays.asList("add", "list", "equip", "remove"),
                    CosmeticsLib.getPlugin().getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()),
                    new ArrayList<>(CosmeticsLib.getUncCosmeticsController().getRegisteredCosmetics().stream()
                            .map(Cosmetic::getCosmeticKey)
                            .collect(Collectors.toList())
                    ),
                    Arrays.asList(""));
        }


        //sort the list
        Collections.sort(result);

        return result;
    }
}
