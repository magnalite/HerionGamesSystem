package herionGamesSystem.Commands;

import herionGamesSystem.PlayerManager.GameManager.SurvivalGamesMapReset;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadSurvivalGames {
	
	public static void Command(CommandSender sender, Command cmd, String label, String[] args) {
		
		SurvivalGamesMapReset.ResetSurvivalMap();
		
	}
}
