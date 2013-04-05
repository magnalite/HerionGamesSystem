package herionGamesSystem.PlayerManager.GameManager;

import herionGamesSystem.PlayerManager.HerionPlayerManager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BasicTeamDeathMatch {
	
	public static HashMap<Player, Integer> PlayersInMatch = new HashMap<Player, Integer>(); //Int = kills
	public static HashMap<Player, Long> PlayersInWaiting = new HashMap<Player, Long>();//Long = time put into hashmap
	public static boolean InMatch = false;
	public static HashMap<Player, String> HerionPlayerGame = HerionPlayerManager.HerionPlayerGame;
	public static HashMap<Player, String> HerionPlayerWait = HerionPlayerManager.HerionPlayerWait;
	public static HashMap<Player, Integer> GreenTeam = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> RedTeam = new HashMap<Player, Integer>();
	public static HashMap<Player, String> PlayerInTeam = new HashMap<Player, String>();
	public static int GreenTeamKills;
	public static int RedTeamKills;
	
	public static boolean addPlayerToGame(Player player){
		
		if(HerionPlayerWait.containsKey(player) == true ){
			
			player.sendMessage(ChatColor.DARK_RED + "You are already waiting to join " + HerionPlayerWait.get(player));
			return true;
		}
		PlayersInWaiting.put(player, System.currentTimeMillis());
		HerionPlayerWait.put(player, "Basic team deathmatch");
		boolean testupdate = updateWaitingList(player);
		if (testupdate == false ){
			player.sendMessage("Sorry but an error occured, please try again.");
			PlayersInWaiting.remove(player);
			HerionPlayerWait.remove(player);
			return true;
		}
		return false;
	}
	
	public static boolean updateWaitingList(Player player){
		
		if(HerionPlayerGame.containsKey(player) == true ){
			
			player.sendMessage(ChatColor.DARK_RED + "Sorry but you can't join a game whilst you are in a " + HerionPlayerGame.get(player));
			PlayersInWaiting.remove(player);
			HerionPlayerWait.remove(player);
			return true;
		}
		
		
		if(InMatch == true){
			
			player.sendMessage(ChatColor.GOLD + "Sorry but there is currently a match in progress so you have been added to the waiting list");
			broadcastUpdate(player);
			
			return true;
		}
		
		if(PlayersInWaiting.size() <= 2){
			
			player.sendMessage(ChatColor.GOLD + "Sorry but there are currently not enough players to start a match so you have been added to the waiting list");
			broadcastUpdate(player);
			
			return true;
		}
		
		if(PlayersInWaiting.size() > 2 && InMatch == false){
			
			for(Player key : PlayersInWaiting.keySet()){
				System.out.println(key.getDisplayName());
				key.sendMessage(ChatColor.GREEN + "Attempting to add you to a team deathmatch!");
				HerionPlayerGame.put(key, "Basic team deathmatch");
				PlayersInMatch.put(key, 0);
				HerionPlayerWait.remove(key);
				key.teleport(player.getServer().getWorld("BasicTeamDeathmatch").getSpawnLocation());
			}
			PlayersInWaiting.values().removeAll(PlayersInWaiting.values());
			beginGame();
			return true;
		}
		return false;
	}
	
	public static void simpleUpdateWaitingList(){
		if(PlayersInWaiting.size() > 2 && InMatch == false){
			
			for(Player key : PlayersInWaiting.keySet()){
				
				key.sendMessage(ChatColor.GREEN + "Attempting to add you to a team deathmatch!");
				PlayersInMatch.put(key, 0);
				PlayersInWaiting.remove(key);
				HerionPlayerWait.remove(key);
				key.teleport(key.getServer().getWorld("BasicTeamDeathmatch").getSpawnLocation());
				
			}
			beginGame();
		}
		
	}
	
	public static void broadcastUpdate(Player player){
		
		for(Player key : PlayersInWaiting.keySet()){
			
			key.sendMessage(ChatColor.AQUA + player.getDisplayName() + " has been added to the waiting list!");
			key.sendMessage(ChatColor.GREEN + "There are now " + PlayersInWaiting.size() + " player(s) currently waiting.");
			
		}
		
	}
	
	public static void beginGame(){
		
		setTeams();
		
	}
	
	public static void setTeams(){
		
		boolean cycle = false;
		
		for(Player key : PlayersInMatch.keySet()){
			if(cycle == false){
				
				PlayerInTeam.put(key, "Green");
				GreenTeam.put(key, 0);
				key.sendMessage(ChatColor.GOLD + "You have been assigned to the" + ChatColor.GREEN + " green team");
				key.getInventory().clear();
				key.getEquipment().clear();
				key.getInventory().addItem(new ItemStack(276),new ItemStack(261),new ItemStack(262, 64),new ItemStack(320, 64));
				cycle = true;
			}
			else{
				
				PlayerInTeam.put(key, "Red");
				RedTeam.put(key, 0);
				key.sendMessage(ChatColor.GOLD + "You have been assigned to the" + ChatColor.RED + " red team");
				key.getInventory().clear();
				key.getEquipment().clear();
				key.getInventory().addItem(new ItemStack(276),new ItemStack(261),new ItemStack(262, 64),new ItemStack(320, 64));
				cycle = false;
			}
				
		}
		
	}
	
	public static void DeathReport(PlayerDeathEvent event){
		
		event.getDrops().clear();
		event.getEntity().sendMessage(ChatColor.RED + "Sorry but you have been killed by " + event.getEntity().getKiller().getDisplayName());
		event.getEntity().getInventory().clear();
		
		if(PlayerInTeam.get(event.getEntity()) == "Green"){
			
			GreenTeam.remove(event.getEntity());
			
		}
		if(PlayerInTeam.get(event.getEntity()) == "Red"){
			
			RedTeam.remove(event.getEntity());
			
		}
		
		PlayersInMatch.remove(event.getEntity());
		HerionPlayerGame.remove(event.getEntity());
		
		if(PlayerInTeam.get(event.getEntity().getKiller()) == "Green"){
			
			GreenTeam.put(event.getEntity().getKiller(), GreenTeam.get(event.getEntity().getKiller())+1);
			GreenTeamKills++;
			
		}
		if(PlayerInTeam.get(event.getEntity().getKiller()) == "Red"){
			
			RedTeam.put(event.getEntity().getKiller(), RedTeam.get(event.getEntity().getKiller())+1);
			RedTeamKills++;
		}
		
		PlayersInMatch.put(event.getEntity().getKiller(), PlayersInMatch.get(event.getEntity().getKiller())+1);
		event.getEntity().getKiller().sendMessage("Congratulations on killing " + event.getEntity().getDisplayName() + ", you now have " + PlayersInMatch.get(event.getEntity().getKiller()) + " kill(s)!");
		for(Player key : PlayersInMatch.keySet()){
			key.sendMessage(ChatColor.DARK_GREEN + event.getEntity().getDisplayName() + " on the " + PlayerInTeam.get(event.getEntity()) + " team has been killed! ");
			key.sendMessage(ChatColor.GREEN + "" + GreenTeam.size() + " green members " + ChatColor.WHITE + "and " + ChatColor.RED + RedTeam.size() + " red members " + ChatColor.WHITE + "remain.");
		}
		
		PlayerInTeam.remove(event.getEntity());
		
		if(GreenTeam.isEmpty()){
			for(Player key : PlayersInMatch.keySet()){
				key.getInventory().clear();
				HerionPlayerGame.remove(key);
				key.sendMessage(ChatColor.GREEN + "The red team has won with " + RedTeamKills + " kills!");
				PlayersInMatch.remove(key);
				key.teleport(key.getServer().getWorld("World").getSpawnLocation());
				key.setHealth(key.getMaxHealth());
			}
			RedTeam.values().removeAll(RedTeam.values());
			simpleUpdateWaitingList();
		}
		if(RedTeam.isEmpty()){
			for(Player key : PlayersInMatch.keySet()){
				key.getInventory().clear();
				HerionPlayerGame.remove(key);
				key.sendMessage(ChatColor.GREEN + "The green team has won with " + GreenTeamKills + " kills!");
				PlayersInMatch.remove(key);
				key.teleport(key.getServer().getWorld("World").getSpawnLocation());
				key.setHealth(key.getMaxHealth());
			}
			GreenTeam.values().removeAll(GreenTeam.values());
			simpleUpdateWaitingList();
		}
	}
}