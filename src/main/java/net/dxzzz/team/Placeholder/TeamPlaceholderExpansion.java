package net.dxzzz.team.Placeholder;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.dxzzz.team.Team;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class TeamPlaceholderExpansion extends PlaceholderExpansion {
    private static TeamPlaceholderExpansion instance;
    private String identifier;
//    private JavaPlugin plugin;
    public static TeamPlaceholderExpansion getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new TeamPlaceholderExpansion("team");
        }
//        this.plugin = plugin;
        return instance;
    }

    public TeamPlaceholderExpansion(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getAuthor() {
        return "X_32mx";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // 职务
        String username = player.getName();
        List<String> playerInfo = Team.GetDatabaseManager().GetPlayerInfo(username);
        if(playerInfo==null){
            return "";
        }
        if (identifier.equals("title")) {
           return playerInfo.get(1);
        }
        else if(identifier.equals("members")){
            List<String> members = Team.GetDatabaseManager().GetTeamMembers(playerInfo.get(0));
            if(members!=null) {
                members.add(0, player.getName());
                return String.join("|", members);
            }
            else {
                List<String> historyMembers = Team.GetDatabaseManager().GetRecentInfo(player.getName());
                if (historyMembers != null) {
                    historyMembers.add(0, player.getName());
                    return String.join("|", historyMembers);
                }
                else {
                    return null;
                }
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        // 职务
        String username = player.getName();
        List<String> playerInfo = Team.GetDatabaseManager().GetPlayerInfo(username);
        System.out.printf("1");
//        plugin.getLogger().info(playerInfo.toString());
        if (identifier.equals("title")) {
            return playerInfo.get(1);
        }
        else if(identifier.equals("members")){
            if(playerInfo!=null) {
                //说明玩家有队伍
                List<String> members = Team.GetDatabaseManager().GetTeamMembers(playerInfo.get(0));
                //获取队长名字
                String captain = Team.GetDatabaseManager().GetTeamBasicInfo(playerInfo.get(0)).get(1);
                members.remove(captain);
                members.add(0,captain);
                return String.join("|", members);
            }
            else{
                //说明玩家没有队伍
                List<String> historyMembers = Team.GetDatabaseManager().GetRecentInfo(player.getName());
                if (!historyMembers.isEmpty()) {
                    //说明玩家曾经有队伍
                    historyMembers.add(player.getName());
                    return String.join("|", historyMembers);
                }
                else {
                    //说明玩家曾经没有队伍
                    return null;
                }
            }
        }else {
            return null;
        }
    }

}