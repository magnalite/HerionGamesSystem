package herionGamesSystem;

import herionGamesSystem.Commands.JoinBasicFreeForAll;
import herionGamesSystem.Commands.JoinBasicTeamDeathmatch;
import herionGamesSystem.Commands.ReloadSurvivalGames;
import herionGamesSystem.Commands.SqlReload;
import herionGamesSystem.Listeners.blocklistener;
import herionGamesSystem.Listeners.playerlistener;
import herionGamesSystem.MySqlHandler.MySqlHander;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class HerionGamesSystem extends JavaPlugin{
	
	public static HerionGamesSystem instance;
	public static FileConfiguration ConfigFile;
	public final playerlistener PlayerListener = new playerlistener();
	public final blocklistener BlockListener = new blocklistener();
	public final Logger log = Logger.getLogger("Minecraft");
	public File ServerFolder = new File("");
	public File BackupSurvivalGames = new File("BackupSurvivalGames");
	public File SurvivalGames = new File("SurvivalGames");
	public File SurvivalGamesuid = new File("SurvivalGames/uid.dat");
	
	@Override
	public void onDisable(){
		getLogger().info("HerionGamesSystem System has been disbled ):");
		
	}
	
	@Override
	public void onEnable(){
		
		instance = this;
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.PlayerListener, this);
		pm.registerEvents(this.BlockListener, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		ConfigFile = getConfig();
		getLogger().info("Loading herioncraft pvp worlds");
		LoadExtraWorlds();
		getLogger().info("Finished Loading herioncraft pvp worlds, now loading MySql data");
		playerlistener.SqlSet = MySqlHander.find(new String[]{"SELECT * FROM `PlayerStats`;"});
		getLogger().info("HerionGamesSystem System has been enabled!");;
	}
	
	public void LoadExtraWorlds(){
		WorldCreator.name("BasicFreeForAll").environment(Environment.NORMAL).createWorld();
		WorldCreator.name("BasicTeamDeathmatch").environment(Environment.NORMAL).createWorld();
		loadSurvivalGames();
	}
	
	public void loadSurvivalGames(){
		
		if(SurvivalGames.exists()){
			
			getLogger().info("Deleting old Suvival Games map");
			try {
				org.apache.commons.io.FileUtils.deleteDirectory(SurvivalGames);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(ChatColor.RED + "FATAL ERROR, FAILED TO DELETE OLD SURVIVAL GAMES MAP! INSTANT RESTART REQUIRED!!");
			}
			
		}
		
		getLogger().info("Copying new SurvivalGames map");
		try {
			org.apache.commons.io.FileUtils.copyDirectory(BackupSurvivalGames, SurvivalGames);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(ChatColor.RED + "FATAL ERROR, FAILED TO COPY SURVIVAL GAMES MAP! INSTANT RESTART REQUIRED!!");
		}
		getLogger().info("Copying Sucessful!");
		getLogger().info("Removing duplicate uid");
		SurvivalGamesuid.delete();
		getLogger().info("Continuing loading of worlds");
		WorldCreator.name("SurvivalGames").environment(Environment.NORMAL).createWorld();
		WorldCreator.name("BackupSurvivalGames").environment(Environment.NORMAL).createWorld();
		this.getServer().getWorld("SurvivalGames").setAutoSave(false);
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("hgs")){
			
			if(args[0].equalsIgnoreCase("ReloadSql")){
				
				SqlReload.Command(sender,cmd,label,args);
				
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("ReloadSurvivalGames")){
				
				ReloadSurvivalGames.Command(sender,cmd,label,args);
				
				return true;
				
			}
			
			
			if(args[0].equalsIgnoreCase("join")){
				
				if(args[1].equalsIgnoreCase("ffa")){
					
					JoinBasicFreeForAll.command(sender, cmd, label, args);
					return true;
				}
				
				if(args[1].equalsIgnoreCase("tdm")){
					
					JoinBasicTeamDeathmatch.command(sender, cmd, label, args);
					return true;
				}
				
				return false;
				
			}
			
			return false;
		}
		return false; 
	}

}
