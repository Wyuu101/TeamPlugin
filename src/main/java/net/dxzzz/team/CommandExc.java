package net.dxzzz.team;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandExc implements CommandExecutor {
    private final JavaPlugin plugin;


    public CommandExc(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public  boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender) {
            if(args[0].equals("gui")) {
                if (args.length < 2) {
                    sender.sendMessage("§b§l组队系统>>§c您输入的参数有误=w=");
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player.isOnline()) {
                    Function.OpenGuiForPlayer(player);
                    return true;
                } else {
                    sender.sendMessage("§b§l组队系统>>§c该玩家不在线");
                    return true;
                }
            }
        }


        if (!(sender instanceof Player)) {
            sender.sendMessage("§b§l组队系统>>§c该指令只能由实体玩家执行");
            return true;
        }
        Player p = (Player) sender;
        if (args.length < 1) {
            return false;
        }
        else {
            if(args[0].equals("cj")){
                if(args.length<2){
                    Function.CreateTeam(p,plugin);
                }
                else{
                    if(Function.CreateTeam(p,args[1],plugin)){
                       return true;
                    }
                }
                return true;
            }
            else if(args[0].equals("xx")){
                Function.ShowTeamInfo(p,plugin);
            }
            else if(args[0].equals("lt")){
                Function.SwitchTeamChat(p,plugin);
                return true;
            }
            else if(args[0].equals("tc")){
                Function.QuitTeam(p,plugin);
                return true;
            }
            else if(args[0].equals("zr")){
                if(args.length<2) {
                    Function.SendMessage_WrongArgs(p);
                    return true;
                }
                else {
                    String newCaptain =  Team.GetDatabaseManager().GetUserRealName(args[1]);
                    Function.TransferTeam(p,newCaptain,plugin);
                    return true;
                }
            }
            else if(args[0].equals("yq")){
                if(args.length<2) {
                    Function.SendMessage_WrongArgs(p);
                    return true;
                }
                else {
                    String guest =  Team.GetDatabaseManager().GetUserRealName(args[1]);
                    Function.InvitePlayer(p,guest,plugin);
                    return true;
                }
            }
            else if(args[0].equals("js")){
                if(args.length<2) {
                    Function.SendMessage_WrongArgs(p);
                    return true;
                }
                else {
                    String captain =  Team.GetDatabaseManager().GetUserRealName(args[1]);
                    Function.AcceptInvite(p,captain,plugin);
                    return true;
                }
            }
            else if(args[0].equals("t")){
                if(args.length<2) {
                    Function.SendMessage_WrongArgs(p);
                    return true;
                }
                else {
                    String aim_kick =  Team.GetDatabaseManager().GetUserRealName(args[1]);
                    Function.KickMember(p,aim_kick,plugin);
                    return true;
                }
            }
            else if(args[0].equals("zj")){
                Function.InviteRecentPlayers(p,plugin);
                return true;
            }
            else if(args[0].equals("gui")){
                if(!p.isOp()){
                    return false;
                }
                if(args.length<2) {
                    Function.SendMessage_WrongArgs(p);
                    return true;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player.isOnline()){
                    Function.OpenGuiForPlayer(player);
                    return true;
                }
                else {
                    p.sendMessage("§b§l组队系统>>§c该玩家不在线");
                    return true;
                }
            }

            else {
                return false;
            }
        }
        return true;
    }
}
