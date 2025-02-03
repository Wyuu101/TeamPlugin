package net.dxzzz.team.Database;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;
import net.dxzzz.team.Utils.UniversalModule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager_Team {
    private final Database mainDb;
    private final Database userDb;
    public DatabaseManager_Team(String username, String password, String host, int port){
        String address = host+":"+port;
        DatabaseOptions mainDb_options= DatabaseOptions.builder().mysql(username, password, "Team", address).build();
        DatabaseOptions userDb_options= DatabaseOptions.builder().mysql(username, password, "authme", address).build();
//        mainDb_options.setDriverClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
//        userDb_options.setDriverClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        mainDb = PooledDatabaseOptions.builder().options(mainDb_options).createHikariDatabase();
        userDb = PooledDatabaseOptions.builder().options(userDb_options).createHikariDatabase();
    }

    public Database GetMainDb (){
        return mainDb;
    }
    public Database GetUserDb (){
        return userDb;
    }


    public void CreateForm_TeamInfo() {
        try {
            mainDb.executeUpdate("CREATE TABLE IF NOT EXISTS TeamInfo (" +
                    "teamName VARCHAR(255) PRIMARY KEY," +
                    "num INT NOT NULL DEFAULT 1," +
                    "captain VARCHAR(255) NOT NULL," +
                    "members VARCHAR(255) NOT NULL" +
                    ")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void CreateForm_PlayerInfo() {
        try {
            mainDb.executeUpdate("CREATE TABLE IF NOT EXISTS PlayerInfo (" +
                    "username VARCHAR(255) PRIMARY KEY," +
                    "teamName VARCHAR(255) NOT NULL," +
                    "title VARCHAR(255) NOT NULL," +
                    "teamChat int NOT NULL DEFAULT 0" +
                    ")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void CreateForm_InviteInfo() {
        try {
            mainDb.executeUpdate("CREATE TABLE IF NOT EXISTS InviteInfo (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(255) NOT NULL," +
                    "guest VARCHAR(255) NOT NULL," +
                    "time BIGINT NOT NULL" +
                    ")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void CreateForm_RecentInfo() {
        try {
            mainDb.executeUpdate("CREATE TABLE IF NOT EXISTS RecentInfo (" +
                    "username VARCHAR(255) PRIMARY KEY," +
                    "teammates VARCHAR(255) NOT NULL" +
                    ")");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<String> GetAllTeamNames(){
        List<String>  result = new ArrayList<>();
        try{
            result = mainDb.getFirstColumnResults("SELECT teamName FROM TeamInfo");
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public void AddTeamInfo(String teamName,int num,String captain,String members){
        try{
            mainDb.executeUpdate("INSERT INTO TeamInfo (teamName, num, captain, members) VALUES (?, ?, ?, ?)",teamName,num,captain,members);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddPlayerInfo(String username,String teamName,String title){
        try{
            mainDb.executeUpdate("INSERT INTO PlayerInfo (username, teamName, title) VALUES (?, ?, ?)",username,teamName,title);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddInviteInfo(String username,String guest_name,long time){
        try{
            mainDb.executeUpdate("INSERT INTO InviteInfo (username, guest, time) VALUES (?, ?, ?)",username,guest_name,time);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SetInviteTime(int id,long time){
        try{
            mainDb.executeUpdate("UPDATE InviteInfo SET time = ? WHERE id = ?",time,id);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int GetInviteId(String username,String guest){
        int id = -1;
        try {
            id = mainDb.getFirstColumn("SELECT id FROM InviteInfo WHERE username = ? AND guest = ?", username,guest);
            return id;
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            return -1;
        }
        return id;
    }

    public  List<String> GetPlayerInfo(String username){
        List<String> result = new ArrayList<>();
        try{
            String teamName = mainDb.getFirstColumn("SELECT teamName FROM PlayerInfo WHERE username = ?",username);
            if(teamName==null){
                return null;
            }
            String title =mainDb.getFirstColumn("SELECT title FROM PlayerInfo WHERE username = ?",username);
            int teamChat =mainDb.getFirstColumn("SELECT teamChat FROM PlayerInfo WHERE username = ?",username);
            result.add(teamName);
            result.add(title);
            result.add(String.valueOf(teamChat));
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  List<String> GetTeamBasicInfo(String teamName){
        List<String> result = new ArrayList<>();
        try{
            int num = mainDb.getFirstColumn("SELECT num FROM TeamInfo WHERE teamName = ?",teamName);
            String captain =mainDb.getFirstColumn("SELECT captain FROM TeamInfo WHERE teamName = ?",teamName);
            result.add(String.valueOf(num));
            result.add(captain);
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public List<String> GetRecentInfo(String username){
        List<String> result = new ArrayList<>();
        try{
            String members =mainDb.getFirstColumn("SELECT teammates FROM RecentInfo WHERE username = ?",username);
            if(members==null){
                return result;
            }
            result = UniversalModule.StringToList(members);
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public void UpdateRecentInfo(String username,String teammates){
        try{
            mainDb.executeUpdate("INSERT INTO RecentInfo (username, teammates) VALUES (?, ?) ON DUPLICATE KEY UPDATE teammates = VALUES(teammates)",username,teammates);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<String> GetTeamMembers(String teamName){
        List<String> result = new ArrayList<>();
        try{
            String members =mainDb.getFirstColumn("SELECT members FROM TeamInfo WHERE teamName = ?",teamName);
            result = UniversalModule.StringToList(members);
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public List<String> GetAllTeamMembers(String teamName){
        List<String> result = new ArrayList<>();
        try{
            String captain =mainDb.getFirstColumn("SELECT captain FROM TeamInfo WHERE teamName = ?",teamName);
            String members =mainDb.getFirstColumn("SELECT members FROM TeamInfo WHERE teamName = ?",teamName);
            result = UniversalModule.StringToList(members);
            result.add(0,captain);
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }


    public void SetPlayerTeamChat(String username ,int num){
        try{
            mainDb.executeUpdate("UPDATE PlayerInfo SET teamChat = ? WHERE username = ?",num,username);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SetTeamInfo(String teamName ,int num,String captain,String members_S){
        try{
            mainDb.executeUpdate("UPDATE TeamInfo SET num = ?, captain = ?, members = ? WHERE teamName = ?",num,captain,members_S,teamName);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void SetPlayerInfo(String username ,String teamName,String  title,int teamChat){
        try{
            mainDb.executeUpdate("UPDATE PlayerInfo SET teamName = ?, title = ?, teamChat = ? WHERE username = ?",teamName,title,teamChat,username);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RemovePlayer(String username){
        try{
            mainDb.executeUpdate("DELETE FROM PlayerInfo WHERE username = ?",username);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RemoveTeam(String teamName){
        try{
            mainDb.executeUpdate("DELETE FROM TeamInfo WHERE teamName = ?",teamName);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RemoveInviteInfo(int id){
        try{
            mainDb.executeUpdate( "DELETE FROM InviteInfo WHERE id = ?",id);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RemoveInviteInfo_ByUserName(String username){
        try{
            mainDb.executeUpdate( "DELETE FROM InviteInfo WHERE username = ?",username);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //BC用
    //public void ClearTables(){
    //    try{
    //        mainDb.executeUpdate( "DELETE FROM TeamInfo");
    //        mainDb.executeUpdate( "DELETE FROM PlayerInfo");
    //        mainDb.executeUpdate( "DELETE FROM InviteInfo");
    //    }catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //}

    public void CloseDataBase(){
        if(mainDb!=null){
            mainDb.close();
        }
        if(userDb!= null){
            userDb.close();
        }
        DB.close();
    }



    public String GetUserRealName(String username){
        String realname = username;
        try{
            realname = userDb.getFirstColumn("SELECT realname FROM authme WHERE username = ?",username);
            if(realname==null){
                realname= username;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return realname;
    }


    public int GetNumOfInviteData(){
        int count =0;
        try{
            List<String> idList=mainDb.getFirstColumnResults( "SELECT id FROM InviteInfo");
            if(idList!=null){
                count=idList.size();
            }

        }catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return count;
    }









    public  List<Integer> GetIdList(){
        try{
            List<Integer>  idList=mainDb.getFirstColumnResults( "SELECT id FROM InviteInfo");
            if(idList!=null){
                return idList;
            }
            else {
                return new ArrayList<>();
            }

        }catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<Long> GetTimeList(){
        try{
            List<Long>  timeList=mainDb.getFirstColumnResults( "SELECT time FROM InviteInfo");
            if(timeList!=null){
                return timeList;
            }
            else {
                return new ArrayList<>();
            }

        }catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
