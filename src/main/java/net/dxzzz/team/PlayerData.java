package net.dxzzz.team;

import net.dxzzz.team.Database.DatabaseManager_Team;

import java.util.List;

public class PlayerData {
    private boolean isInTeam = false;
    private String teamName = null;
    private String title = null;
    private String teamChat = null;
    private String captain = null;
    private List<String> allMembers = null;
    private int numOfMembers = 0;
    private List<String> historyMembers = null;
    private static DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
    public PlayerData(String playerName) {
        loadPlayerData(playerName);
    }
    private void loadPlayerData(String playerName) {
        List<String> playerInfo = databaseManager.GetPlayerInfo(playerName);
        if(playerInfo != null) {
            // 玩家在队伍中
            this.isInTeam = true;
            this.teamName = playerInfo.get(0);
            this.title = playerInfo.get(1);
            this.teamChat = playerInfo.get(2);
            this.allMembers = databaseManager.GetAllTeamMembers(this.teamName);
            this.numOfMembers = allMembers.size();
            this.captain = allMembers.get(0);
            this.historyMembers = databaseManager.GetRecentInfo(playerName);
        }
        else {
            this.historyMembers = databaseManager.GetRecentInfo(playerName);
        }
    }
    public String getTeamName() {
        return teamName;
    }
    public String getTitle() {
        return title;
    }
    public String getTeamChat() {
        return teamChat;
    }
    public String getCaptain() {
        return captain;
    }
    public List<String> getAllMembers() {
        return allMembers;
    }
    public List<String> getHistoryMembers() {
        return historyMembers;
    }
    public boolean getIsInTeam() {
        return isInTeam;
    }
    public int getNumOfMembers() {
        return numOfMembers;
    }
}
