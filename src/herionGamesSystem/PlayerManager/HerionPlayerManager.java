package herionGamesSystem.PlayerManager;

import herionGamesSystem.PlayerManager.GameManager.BasicFreeForAll;
import herionGamesSystem.PlayerManager.GameManager.BasicTeamDeathMatch;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HerionPlayerManager {
	
	public static HashMap<Player, World> HerionPlayerWorld = new HashMap<Player, World>();
	public static HashMap<Player, String> HerionPlayerGame = new HashMap<Player, String>();
	public static HashMap<Player, String> HerionPlayerWait = new HashMap<Player, String>();
	
	public static void updateWorld(Player player, World world){
		
		HerionPlayerWorld.put(player, world);
		
	}
	
	public static World findWorld(Player player){
		
		World world = HerionPlayerWorld.get(player);
		return world;
		
	}
	
	public static void updateGame(Player player, String game){
		
		HerionPlayerGame.put(player, game);
		
	}
	
	public static String findGame(Player player){
		
		String game = HerionPlayerGame.get(player);
		return game;
		
	}
	
	public static void DeathReport(PlayerDeathEvent event){
		
		if(HerionPlayerGame.containsKey(event.getEntity())){
			
			if(HerionPlayerGame.get(event.getEntity()).equalsIgnoreCase("Basic free for all")){
				
				BasicFreeForAll.DeathReport(event);
				
			}
			if(HerionPlayerGame.get(event.getEntity()).equalsIgnoreCase("Basic team deathmatch")){
				
				BasicTeamDeathMatch.DeathReport(event);
				
			}
			
			
		}
		
	}

}
