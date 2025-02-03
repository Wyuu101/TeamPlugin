package net.dxzzz.team;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.dxzzz.team.Database.DatabaseManager_Team;
import net.dxzzz.team.Listener.PluginTeamMessage;
import net.dxzzz.team.Listener.TeamChatListener;
import net.dxzzz.team.Listener.TeamFastInviteListener;
import net.dxzzz.team.Listener.TeamGuiListener;
import net.dxzzz.team.Placeholder.TeamPlaceholderExpansion;
import net.dxzzz.team.ScheduleTask.CheckInvTask;
import net.dxzzz.team.Utils.UniversalModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.logging.Logger;

public final class Team extends JavaPlugin {
    public static PlaceholderAPIPlugin placeholderAPI;
    public final Logger logger= getLogger();
    public static CheckInvTask checkInvTask;
    private static PluginTeamMessage pluginTeamMessage;
    private static TeamChatListener teamChatListener;
    private static TeamGuiListener teamGuiListener;
    private static TeamFastInviteListener teamFastInviteListener;
    private static TeamPlaceholderExpansion teamPlaceholderExpansion;
    private static String username;
    private static String password;
    private int port;
    private String host;
    private static String talkPriority;
    private static String talkPrefix;
    private static Boolean talkDetectCancelOrNot;
    private static int maxPlayers;
    private static boolean allowFastInvite;
    private static boolean loadConfigSuccess ;
    private static DatabaseManager_Team databaseManager;
    @Override
    public void onEnable() {
        logger.info("===========[Team正在加载中]===========");
        logger.info("Author: X_32mx");
        logger.info("QQ: 2644489337");
        logger.info("This plugin is only for Dxzzz.net");
        this.saveDefaultConfig();
        this.reloadConfig();
        this.loadConfig();

        if(!loadConfigSuccess){
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(!this.setupPlaceholderAPI()){
            logger.severe(ChatColor.RED+"未找到PlaceholderAPI前置插件,部分功能将失效。");
        }
        else {
            logger.info("已查找到PlaceholderAPI");
        }
        databaseManager = new DatabaseManager_Team(username, password, host, port);

        if (databaseManager.GetMainDb() == null||databaseManager.GetUserDb()==null) {
            logger.severe("创建数据库连接失败，请检查配置，插件即将自动卸载.");
            loadConfigSuccess = false;
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        databaseManager.CreateForm_TeamInfo();
        databaseManager.CreateForm_PlayerInfo();
        databaseManager.CreateForm_InviteInfo();
        databaseManager.CreateForm_RecentInfo();
        checkInvTask = new CheckInvTask(this);
        teamChatListener= new TeamChatListener(this);
        teamGuiListener = new TeamGuiListener(this);
        teamFastInviteListener = new TeamFastInviteListener();
        pluginTeamMessage = new PluginTeamMessage(this);
        teamPlaceholderExpansion = TeamPlaceholderExpansion.getInstance(this);
        teamPlaceholderExpansion.register();
        Bukkit.getPluginCommand("zd").setExecutor(new CommandExc(this));
        Bukkit.getPluginManager().registerEvents(teamGuiListener, this);
        Bukkit.getPluginManager().registerEvents(teamFastInviteListener, this);
        Bukkit.getPluginManager().registerEvent(AsyncPlayerChatEvent.class,teamChatListener, UniversalModule.GetEventPriority(talkPriority),  new EventExecutor() {
            @Override
            public void execute(Listener listener, Event event) throws EventException {
                if (event instanceof AsyncPlayerChatEvent) {
                    teamChatListener.onPlayerChat((AsyncPlayerChatEvent) event);
                }
            }
        },this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginTeamMessage);
        logger.info("==========[Team组队系统已加载完毕]=========");
    }

    @Override
    public void onDisable() {
        databaseManager.CloseDataBase();
        logger.info("组队系统已卸载");
    }

    public void loadConfig() {


        username = this.getConfig().getString("DataBase.MySQL.Username", "root");
        password = this.getConfig().getString("DataBase.MySQL.Password");
        host = this.getConfig().getString("DataBase.MySQL.Host", "localhost");
        port = this.getConfig().getInt("DataBase.MySQL.Port", 3306);


        talkPriority = this.getConfig().getString("ChatSettings.Priority","LOW");
        talkDetectCancelOrNot =  this.getConfig().getBoolean("ChatSettings.DetectCancelOrNot",false);
        if(!UniversalModule.isTalkPriorityParamTrue(talkPriority)){
            logger.severe(ChatColor.RED+"组队聊天监听优先级配置有误，请前往config.yml检查!");
            logger.severe(ChatColor.RED+"插件开始自动卸载");
            loadConfigSuccess=false;
            return;
        }
        talkPrefix = this.getConfig().getString("ChatSettings.TalkPrefix");
        maxPlayers =this.getConfig().getInt("ChatSettings.MaxPlayers",6);
        allowFastInvite = this.getConfig().getBoolean("FastInviteSettings.Enable",true);
        loadConfigSuccess=true;

    }


    private boolean setupPlaceholderAPI() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return false;
        }
        placeholderAPI = (PlaceholderAPIPlugin) getServer().getPluginManager().getPlugin("PlaceholderAPI");
        return placeholderAPI != null;
    }


    public static DatabaseManager_Team GetDatabaseManager (){
        return databaseManager;
    }
    public static boolean GetTalkDetectCancelOrNot(){
        return talkDetectCancelOrNot;
    }

    public static String GetTalkPrefix(){
        return talkPrefix;
    }
    public static int GetMaxPlayers(){
        return maxPlayers;
    }
    public static boolean GetAllowFastInvite(){
        return allowFastInvite;
    }
}
