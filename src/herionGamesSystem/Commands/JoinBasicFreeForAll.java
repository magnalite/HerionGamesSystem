package herionGamesSystem.Commands;

import herionGamesSystem.PlayerManager.GameManager.BasicFreeForAll;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JoinBasicFreeForAll {
	
	public static void command(CommandSender sender, Command cmd, String label, String[] args){
		
		BasicFreeForAll.addPlayerToGame(sender.getServer().getPlayer(sender.getName()));
		
	}

}
