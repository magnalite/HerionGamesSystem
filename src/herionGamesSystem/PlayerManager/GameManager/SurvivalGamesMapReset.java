package herionGamesSystem.PlayerManager.GameManager;

import java.util.List;

import herionGamesSystem.HerionGamesSystem;
import herionGamesSystem.Listeners.blocklistener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurvivalGamesMapReset {
	
	private static int repeatResetTask = 0;
	
	
	public static int ResetSurvivalMap(){
		
		HerionGamesSystem.instance.getServer().broadcastMessage(ChatColor.BLUE + "Starting reset of survival games map. " + blocklistener.ChunksChanged.size() + " blocks to change. Minor lag possible");
	
	repeatResetTask = HerionGamesSystem.instance.getServer().getScheduler().scheduleSyncRepeatingTask(HerionGamesSystem.instance, new BukkitRunnable(){
	
	public void run() {
	
	
	Object[] ChunksChangedArray = blocklistener.ChunksChanged.keySet().toArray();
	
	if(ChunksChangedArray.length < 50){
		
		for(int i=0; i<ChunksChangedArray.length;i++){
			
			Block block = (Block) ChunksChangedArray[i];
			
			block.setTypeIdAndData(blocklistener.ChunksChanged.get(block).getTypeId(), blocklistener.ChunksChanged.get(block).getData(), false);
			if(blocklistener.ChunksChanged.get(block).getState().getType() == Material.CHEST){
				
				Chest chest = (Chest) block.getState();
				Chest properchest = (Chest) blocklistener.ChunksChanged.get(block).getState();
				chest.getBlockInventory().setContents(properchest.getBlockInventory().getContents());
				
			}
			
			blocklistener.ChunksChanged.remove(ChunksChangedArray[i]);
		}
		HerionGamesSystem.instance.getServer().getScheduler().cancelTask(repeatResetTask);
		List<Entity> AllEntities = HerionGamesSystem.instance.getServer().getWorld("SurvivalGames").getEntities();
		HerionGamesSystem.instance.getServer().broadcastMessage(ChatColor.BLUE + "Removing " + AllEntities.size() + " entities from survival games map.");
		for(int i = 0; i<AllEntities.size(); i++){
			
			if(!(AllEntities.get(i) instanceof Player)){
				
			AllEntities.get(i).remove();
			}
		}
		HerionGamesSystem.instance.getServer().broadcastMessage(ChatColor.BLUE + "Reset of survival games map complete!");
		
	}
	
	else{
	for(int i=0; i<50;i++){
		
		Block block = (Block) ChunksChangedArray[i];
		
		block.setTypeIdAndData(blocklistener.ChunksChanged.get(block).getTypeId(), blocklistener.ChunksChanged.get(block).getData(), false);
		if(blocklistener.ChunksChanged.get(block).getState().getType() == Material.CHEST){
			
			Chest chest = (Chest) block.getState();
			Chest properchest = (Chest) blocklistener.ChunksChanged.get(block).getState();
			chest.getBlockInventory().setContents(properchest.getBlockInventory().getContents());
			
		}
		
		blocklistener.ChunksChanged.remove(ChunksChangedArray[i]);
	}
	}
	}
},(long) 1, (long) 1 );
	return repeatResetTask;
}
}
