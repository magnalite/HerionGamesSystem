package herionGamesSystem.Commands;

import herionGamesSystem.PlayerManager.GameManager.BasicTeamDeathMatch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JoinBasicTeamDeathmatch {
	
	public static void command(CommandSender sender, Command cmd, String label, String[] args){
		
		BasicTeamDeathMatch.addPlayerToGame(sender.getServer().getPlayer(sender.getName()));
		
	}

}
