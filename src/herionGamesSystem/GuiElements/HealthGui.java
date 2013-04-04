package herionGamesSystem.GuiElements;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.GenericLabel;

public class HealthGui extends GenericLabel {
	
	ChatColor chatcolor = ChatColor.GREEN;
	
	@Override
	public void onTick(){
		if(!this.getText().equals("Health " + this.getScreen().getPlayer().getHealth())){
			if(this.getScreen().getPlayer().getHealth() > (this.getScreen().getPlayer().getMaxHealth()/4)*3){
				chatcolor = ChatColor.GREEN;
			}
			if(this.getScreen().getPlayer().getHealth() < (this.getScreen().getPlayer().getMaxHealth()/4)*3  && this.getScreen().getPlayer().getHealth() > (this.getScreen().getPlayer().getMaxHealth()/4)*2){
				chatcolor = ChatColor.YELLOW;
			}
			if(this.getScreen().getPlayer().getHealth() < (this.getScreen().getPlayer().getMaxHealth()/4)*2  && this.getScreen().getPlayer().getHealth() > (this.getScreen().getPlayer().getMaxHealth()/4)*1){
				chatcolor = ChatColor.GOLD;
			}
			if(this.getScreen().getPlayer().getHealth() < (this.getScreen().getPlayer().getMaxHealth()/4)*1){
				chatcolor = ChatColor.DARK_RED;
			}
		this.setText(chatcolor + "Health " + this.getScreen().getPlayer().getHealth() + "/" + this.getScreen().getPlayer().getMaxHealth());
		this.setDirty(true);
		}
		
	}

}
