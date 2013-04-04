package herionGamesSystem.MySqlHandler;

import herionGamesSystem.HerionGamesSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;


public class MySqlHander {
	
	static ResultSet finalrs = null;
	static String username;
	static String password;
	
	  public static ResultSet find(String[] args){
		  if(HerionGamesSystem.instance == null){
			  
			  return finalrs;
		  }
		  username = HerionGamesSystem.instance.getConfig().getString("Sql Login");
		  password = HerionGamesSystem.instance.getConfig().getString("Sql Password");
		  final String[] arguments = args;
		  HerionGamesSystem.instance.getServer().getScheduler().runTaskAsynchronously(HerionGamesSystem.instance, new BukkitRunnable(){

			  @Override
				public void run() {
				  ResultSet rs = null;
		    Connection connection = null;
		    Statement statement = null;
		      try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		      String url = "jdbc:mysql://localhost/baklit";
		      try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				HerionGamesSystem.instance.getServer().broadcastMessage(ChatColor.DARK_RED + "**CONTACT AN ADMIN ASAP**The MySql details in the config.yml are incorrect! All store/economy function are now non-operational and WILL " + ChatColor.RED + "NOT" + ChatColor.DARK_RED + " SAVE!!");
			}

		      try {
				statement = connection.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      String hrappSQL = arguments[0];
			try {
				System.out.println("Accessing MySQL");
				rs = statement.executeQuery(hrappSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalrs = rs;
		  }
		  });
		  return finalrs;
	  }
	  
	  public static void set(String[] args){
		  
		  username = HerionGamesSystem.instance.getConfig().getString("Sql Login");
		  password = HerionGamesSystem.instance.getConfig().getString("Sql Password");
		  
		  final String[] arguments = args;
		  HerionGamesSystem.instance.getServer().getScheduler().runTaskAsynchronously(HerionGamesSystem.instance, new BukkitRunnable(){

			  @Override
				public void run() {
		  
		  
		    Connection connection = null;
		    Statement statement = null;
		      try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		      String url = "jdbc:mysql://localhost/baklit";
		      try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				HerionGamesSystem.instance.getServer().broadcastMessage(ChatColor.DARK_RED + "**CONTACT AN ADMIN ASAP**The MySql details in the config.yml are incorrect! All store/economy function are now non-operational and WILL " + ChatColor.RED + "NOT" + ChatColor.DARK_RED + " SAVE!!");
			}

		      try {
				statement = connection.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		      String hrappSQL = arguments[0];
			try {
				System.out.println("Accessing MySQL");
				statement.executeUpdate(hrappSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  }
		  });
	  }
}
