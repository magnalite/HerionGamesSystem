package herionGamesSystem.PlayerManager.GameManager;

import herionGamesSystem.PlayerManager.HerionPlayerManager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BasicFreeForAll {
	
	public static HashMap<Player, Integer> PlayersInMatch = new HashMap<Player, Integer>(); //Integer = kills
	public static HashMap<Player, Long> PlayersInWaiting = new HashMap<Player, Long>();//Long = time put into hashmap
	public static boolean InMatch = false;
	public static HashMap<Player, String> HerionPlayerGame = HerionPlayerManager.HerionPlayerGame;
	public static HashMap<Player, String> HerionPlayerWait = HerionPlayerManager.HerionPlayerWait;
	
	public static boolean addPlayerToGame(Player player){
		
		if(HerionPlayerWait.containsKey(player) == true ){
			
			player.sendMessage(ChatColor.DARK_RED + "You are already waiting to join " + HerionPlayerWait.get(player));
			return true;
		}
		PlayersInWaiting.put(player, System.currentTimeMillis());
		HerionPlayerWait.put(player, "Basic free for all");
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
				key.sendMessage(ChatColor.GREEN + "Attempting to add you to a free for all match!");
				HerionPlayerGame.put(key, "Basic free for all");
				PlayersInMatch.put(key, 0);
				HerionPlayerWait.remove(key);
				key.teleport(player.getServer().getWorld("BasicFreeForAll").getSpawnLocation());
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
				
				key.sendMessage(ChatColor.GREEN + "Attempting to add you to a free for all match!");
				PlayersInMatch.put(key, 0);
				PlayersInWaiting.remove(key);
				HerionPlayerWait.remove(key);
				key.teleport(key.getServer().getWorld("BasicFreeForAll").getSpawnLocation());
				
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
		
		for(Player key : PlayersInMatch.keySet()){
			key.sendMessage(ChatColor.DARK_GREEN + "You are up against " + PlayersInMatch.size() + " player(s)!" + ChatColor.DARK_RED + " BEGIN!");
			key.getInventory().clear();
			key.getEquipment().clear();
			key.getInventory().addItem(new ItemStack(276),new ItemStack(261),new ItemStack(262, 64),new ItemStack(320, 64));
			
		}
		
	}
	
	public static void DeathReport(PlayerDeathEvent event){
		
		event.getDrops().clear();
		event.getEntity().sendMessage(ChatColor.RED + "Sorry but you have been killed by " + event.getEntity().getKiller().getDisplayName());
		event.getEntity().getInventory().clear();
		PlayersInMatch.remove(event.getEntity());
		HerionPlayerGame.remove(event.getEntity());
		PlayersInMatch.put(event.getEntity().getKiller(), PlayersInMatch.get(event.getEntity().getKiller())+1);
		event.getEntity().getKiller().sendMessage("Congratulations on killing " + event.getEntity().getDisplayName() + ", you now have " + PlayersInMatch.get(event.getEntity().getKiller()) + " kill(s)!");
		for(Player key : PlayersInMatch.keySet()){
			key.sendMessage(ChatColor.DARK_GREEN + event.getEntity().getDisplayName() + " has been killed! " + PlayersInMatch.size() + " players remain.");
		}
		
		if(PlayersInMatch.size() == 1){
			for(Player key : PlayersInMatch.keySet()){
				key.getInventory().clear();
				key.getEquipment().clear();
				HerionPlayerGame.remove(key);
				key.sendMessage(ChatColor.GREEN + "Congratulations you are the victor!");
				key.getServer().broadcastMessage(ChatColor.GOLD + key.getDisplayName() + " Has just won a free for all game with " + PlayersInMatch.get(key) + " kill(s)!");
				PlayersInMatch.remove(key);
				simpleUpdateWaitingList();
				key.teleport(key.getServer().getWorld("World").getSpawnLocation());
				key.setHealth(key.getMaxHealth());
			}
			
			
		}
	}
	
	

}
