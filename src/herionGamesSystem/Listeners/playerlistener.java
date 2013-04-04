package herionGamesSystem.Listeners;

import herionGamesSystem.HerionGamesSystem;
import herionGamesSystem.MySqlHandler.MySqlHander;
import herionGamesSystem.PlayerManager.HerionPlayerManager;
import herionGamesSystem.PlayerManager.GameManager.BasicFreeForAll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutcraftFailedEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class playerlistener implements Listener{
	public HerionGamesSystem plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public static ResultSet SqlSet = MySqlHander.find(new String[]{"SELECT * FROM `PlayerStats`;"});
	
	HashMap<SpoutPlayer, Boolean> SpoutPlayers = new HashMap<SpoutPlayer, Boolean>();
	HashMap<SpoutPlayer, Boolean> OnWelcome = new HashMap<SpoutPlayer, Boolean>();
	HashMap<SpoutPlayer, UUID> WelcomeMessageUUID = new HashMap<SpoutPlayer, UUID>();
	
	@EventHandler
	public void onSpoutCraftFailed(SpoutcraftFailedEvent event){
		
		event.getPlayer().sendMessage("\u00A74" + "I see your not using spout! Naughty!");
		SpoutPlayers.put(event.getPlayer(), false);
		
	}
	
	@EventHandler
	public void onSpoutCraftEnable(SpoutCraftEnableEvent event){
		
		//GenericContainer WelcomeContainer = new GenericContainer();
		//GenericTexture WelcomeMessage = new GenericTexture();
		
		event.getPlayer().sendMessage("\u00A7a" + "Thank you for using spout to unleash the full gaming experience!");
		
		SpoutPlayers.put(event.getPlayer(), true);
		//OnWelcome.put(event.getPlayer(), true);
		//WelcomeMessageUUID.put(event.getPlayer(), WelcomeMessage.getId());
		
		/*/WelcomeContainer.addChild(WelcomeMessage);
		WelcomeContainer.setAlign(WidgetAnchor.CENTER_CENTER).setWidth(427).setHeight(200);
		WelcomeMessage.setDrawAlphaChannel(true).setUrl("https://dl.dropbox.com/u/46495247/WelcomeToHerionGames.png");
		WelcomeMessage.setMaxWidth(200).setMaxHeight(50);
		SpoutManager.getPlayer(event.getPlayer()).getMainScreen().attachWidget(HerionGamesSystem.instance, WelcomeContainer);
		SpoutManager.getSoundManager().playCustomMusic(this.plugin, event.getPlayer(), "http://soundjax.com/reddo/24718%5Ewelcome.mp3", false);/*/
		
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws SQLException{
		
		
		SqlSet = MySqlHander.find(new String[]{"SELECT * FROM `PlayerStats`;"});
		boolean playerdbfound = false;
		
		
		SqlSet.beforeFirst();
		
		while (SqlSet.next()){
			String playername = SqlSet.getString("PlayerName");
			if(playername.equals(event.getPlayer().getName())){
				playerdbfound = true;
				event.getPlayer().sendMessage("\u00A76" + "Welcome back, " + event.getPlayer().getDisplayName()+ "!");
			}
			
		}
		if(playerdbfound == false){
			
			event.getPlayer().sendMessage("\u00A76" + "Thank you for joining!   (not new? please notify the admins!)");
			MySqlHander.set(new String[]{"INSERT INTO `PlayerStats` (`PlayerName`, `MaxHealth`, `Speed`, `Jump`) VALUES ('" + event.getPlayer().getDisplayName() + "', '20', '1', '1');"});	
		}
			
			
			
}
		
		
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		
		if(SpoutPlayers.get(event.getPlayer()) == true){
			
			SpoutManager.getPlayer(event.getPlayer()).getMainScreen().removeWidgets(HerionGamesSystem.instance);
		}
		
		SpoutPlayers.remove(SpoutManager.getPlayer(event.getPlayer()));
		OnWelcome.remove(SpoutManager.getPlayer(event.getPlayer()));
		WelcomeMessageUUID.remove(SpoutManager.getPlayer(event.getPlayer()));
		HerionPlayerManager.HerionPlayerGame.remove(SpoutManager.getPlayer(event.getPlayer()));
		HerionPlayerManager.HerionPlayerWorld.remove(SpoutManager.getPlayer(event.getPlayer()));
		HerionPlayerManager.HerionPlayerWait.remove(SpoutManager.getPlayer(event.getPlayer()));
		BasicFreeForAll.PlayersInMatch.remove(SpoutManager.getPlayer(event.getPlayer()));
		BasicFreeForAll.PlayersInWaiting.remove(SpoutManager.getPlayer(event.getPlayer()));
	}
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) throws SQLException{
		
		//ResultSet SqlSet = MySqlHander.find(new String[]{"SELECT * FROM `PlayerStats`;"});
		
		SqlSet.beforeFirst();
		
		while (SqlSet.next()){
			String playername = SqlSet.getString("PlayerName");
			if(playername.equals(event.getPlayer().getName())){
				event.getPlayer().setMaxHealth(SqlSet.getInt("MaxHealth"));
				SpoutManager.getPlayer(event.getPlayer()).setWalkingMultiplier(SqlSet.getDouble("Speed"));
				SpoutManager.getPlayer(event.getPlayer()).setAirSpeedMultiplier(SqlSet.getDouble("Speed"));
				SpoutManager.getPlayer(event.getPlayer()).setJumpingMultiplier(SqlSet.getDouble("Jump"));
			}
			
		}
		
		/*/if (OnWelcome.get(event.getPlayer()) == true){
			
			SpoutManager.getPlayer(event.getPlayer()).getMainScreen().getWidget(WelcomeMessageUUID.get(event.getPlayer())).getContainer().removeChild(SpoutManager.getPlayer(event.getPlayer()).getMainScreen().getWidget(WelcomeMessageUUID.get(event.getPlayer())));
			WelcomeMessageUUID.remove(SpoutManager.getPlayer(event.getPlayer()));
			GuiGenerator.createBasicGui(SpoutManager.getPlayer(event.getPlayer()));
			
			
			OnWelcome.put(SpoutManager.getPlayer(event.getPlayer()), false);
			
		}/*/
		
		
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		
		HerionPlayerManager.DeathReport(event);
		event.setDeathMessage("");	
		
		}	
		
}
