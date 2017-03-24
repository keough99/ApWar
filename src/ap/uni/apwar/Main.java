package ap.uni.apwar;

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

public class Main extends JavaPlugin {

	public void onEnable(){	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){


        Player player = (Player)sender;
        
       if(!(sender instanceof Player)){
           sender.sendMessage("You can only use this command from in the game!");
           return true;
       }
           
           if(cmd.getName().equalsIgnoreCase("war")){
               Faction enemyFaction = FactionColl.get().getByName(args[0]); //get the enemy faction
               MPlayer mplayer = MPlayer.get(sender);   //defines mplayer
               Faction faction = mplayer.getFaction();   //get's sender faction
               
                if(mplayer.getRole().isAtLeast(Rel.OFFICER)){
                    if (faction == enemyFaction) {
                        return false;
                    }
                    else {
                    faction.setRelationWish(enemyFaction, Rel.ENEMY);
                    
                    for(Player p : Bukkit.getOnlinePlayers()){
                    	p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1, 0);
                    	}
                    this.getServer().broadcastMessage(faction + " Has gone to war with " + enemyFaction + " !");
                    }
                    
                       }
           }
		return true;
	}
}