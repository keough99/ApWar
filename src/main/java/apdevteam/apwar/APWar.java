package apdevteam.apwar;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class APWar extends JavaPlugin {

    public void onEnable(){	}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("war")){
            if(!(sender instanceof Player)){
                sender.sendMessage("You can only use this command from in the game!");
                return true;
            }
            Faction enemyFaction = FactionColl.get().getByName(args[0]); //get the enemy faction
            MPlayer mplayer = MPlayer.get(sender);   //defines mplayer
            Faction faction = mplayer.getFaction();   //get's sender faction
            if(mplayer.getRole().isAtLeast(Rel.OFFICER)){
                sender.sendMessage("You need to be at least an officer to do that");
                return true;
            }
            if (faction.equals(enemyFaction)) {
                sender.sendMessage("You can't declare war on your own faction!");
                return true;
            }
            if(faction.getRelationTo(enemyFaction).equals(Rel.ENEMY)) {
                sender.sendMessage("You're already at war with " + enemyFaction.getName());
                return true;
            }
            faction.setRelationWish(enemyFaction, Rel.ENEMY);
            for(Player p : Bukkit.getOnlinePlayers()){
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0);
                MPlayer messagePlayer = MPlayer.get(p);
                p.sendMessage(faction.getRelationTo(messagePlayer).getColor()  + faction.getName() + ChatColor.RESET + " has declared war on " +
                        enemyFaction.getRelationTo(messagePlayer).getColor() + enemyFaction.getName());
            }

        }
        return false;
    }
}