package herionGamesSystem.GuiElements;

import java.util.HashMap;
import java.util.UUID;

import herionGamesSystem.HerionGamesSystem;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericEntityWidget;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GuiGenerator {
	
	public static HashMap<SpoutPlayer, UUID> SelfViewUUID = new HashMap<SpoutPlayer, UUID>();
	
	public static void createBasicGui(SpoutPlayer player){
		
		HerionGamesSystem plugin = HerionGamesSystem.instance;
		
		player.getMainScreen().getHealthBar().setMaxNumHearts(0).setDirty(true);
		player.getMainScreen().getHungerBar().setNumOfIcons(0).setDirty(true);
		
		GenericGradient BottomGradient = new GenericGradient();
		BottomGradient.setHeight(10).setWidth(427);
		BottomGradient.setY(player.getMainScreen().getHeight()-BottomGradient.getHeight());
		BottomGradient.setTopColor(new Color(0,0,0,0));
		BottomGradient.setBottomColor(new Color(0,0,0,255));
		player.getMainScreen().attachWidget(plugin, BottomGradient);
		
		GenericLabel healthgui = new HealthGui();
		healthgui.setAuto(true).setX(50).setY(-10).setWidth(30).setHeight(30);
		healthgui.setAnchor(WidgetAnchor.BOTTOM_LEFT);
		player.getMainScreen().attachWidget(plugin, healthgui);
		
		GenericEntityWidget SelfView = new GenericEntityWidget();
		SelfView.setHeight(50).setWidth(25);
		SelfView.setX(10).setY(player.getMainScreen().getHeight()-SelfView.getHeight());
		SelfView.setEntityId(player.getEntityId());
		SelfViewUUID.put(player, SelfView.getId());
		player.getMainScreen().attachWidget(plugin, SelfView);
		
	}

}
