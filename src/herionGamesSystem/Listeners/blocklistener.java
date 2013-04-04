package herionGamesSystem.Listeners;

import herionGamesSystem.HerionGamesSystem;

import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class blocklistener implements Listener{
	
	public static HashMap<Block, Block> ChunksChanged = new HashMap<Block, Block>();
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		
		if(event.getBlock().getWorld().equals(HerionGamesSystem.instance.getServer().getWorld("SurvivalGames"))){
		
		if(!ChunksChanged.containsKey(event.getBlock().getChunk())){
			
			HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()).getChunk().load();
			ChunksChanged.put(event.getBlock(), HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
			}

		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.getBlock().getWorld().equals(HerionGamesSystem.instance.getServer().getWorld("SurvivalGames"))) {
		
		if(!ChunksChanged.containsKey(event.getBlock().getChunk())){
			
			HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()).getChunk().load();
			ChunksChanged.put(event.getBlock(), HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
		}
		}
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event){
		if (event.getBlock().getWorld().equals(HerionGamesSystem.instance.getServer().getWorld("SurvivalGames"))) {

		if(!ChunksChanged.containsKey(event.getBlock().getChunk())){
			
			HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()).getChunk().load();
			ChunksChanged.put(event.getBlock(), HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
		}
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event){ //here to tracks blocks destroyed due to explosion
		
		List<Block> blocklist = event.blockList();
		
		for(Block block : blocklist){
			
			if(block.getWorld().equals(HerionGamesSystem.instance.getServer().getWorld("SurvivalGames"))) {
				
				if(!ChunksChanged.containsKey(block.getChunk())){
					
					HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(block.getX(), block.getY(), block.getZ()).getChunk().load();
					ChunksChanged.put(block, HerionGamesSystem.instance.getServer().getWorld("BackupSurvivalGames").getBlockAt(block.getX(), block.getY(), block.getZ()));
				}
				
				
			}
			
		}
		
	}

}
