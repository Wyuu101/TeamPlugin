package net.dxzzz.team.Listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dxzzz.team.Database.DatabaseManager_Team;
import net.dxzzz.team.Function;
import net.dxzzz.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TeamChatListener implements Listener {
    private final JavaPlugin plugin;
    public static TeamChatListener instance;//

    public TeamChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public static TeamChatListener getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new TeamChatListener(plugin);
        }
        return instance;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(Team.GetTalkDetectCancelOrNot()) {
            if (event.isCancelled()) {
                return;
            }
        }
        Player player = event.getPlayer();
        String username = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        List<String> playerInfo =  databaseManager.GetPlayerInfo(username);
        if(playerInfo == null){
            if(event.getMessage().startsWith("#")){
                Function.SendMessage_NoTeam_Chat(player);
                event.setCancelled(true);
            }
            return;
        }
        else{
            String teamChat = playerInfo.get(2);
            String teamName = playerInfo.get(0);
            if(teamChat.equals("0")){
                if(!event.getMessage().startsWith("#")) {
                    return;
                }
                else {
                    List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                    String finalMessage = PlaceholderAPI.setPlaceholders(player, Team.GetTalkPrefix()) + event.getMessage().substring(1);
                    Function.SendTeamMsg(player,finalMessage,allMembers,plugin);
                    event.setCancelled(true);
                    return;
                }
            }
            else {
                List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                String rawMsg = event.getMessage();
                if(event.getMessage().startsWith("#")) {
                    rawMsg = event.getMessage().substring(1);
                }
                String finalMessage = PlaceholderAPI.setPlaceholders(player, Team.GetTalkPrefix()) + rawMsg;
                Function.SendTeamMsg(player,finalMessage,allMembers,plugin);
                event.setCancelled(true);
                return;
            }
        }


    }
}
