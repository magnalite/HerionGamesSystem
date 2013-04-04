package herionGamesSystem.Commands;

import herionGamesSystem.Listeners.playerlistener;
import herionGamesSystem.MySqlHandler.MySqlHander;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class SqlReload {

	public static void Command(CommandSender sender, Command cmd, String label, String[] args) {
		
		sender.sendMessage("Reloading sql cache");
		//sender.getServer().getPlayer(sender.getName()).teleport(sender.getServer().getPlayer(sender.getName()).getServer().getWorld("SurvivalGames").getSpawnLocation());
		playerlistener.SqlSet = MySqlHander.find(new String[]{"SELECT * FROM `PlayerStats`;"});
	}

}
