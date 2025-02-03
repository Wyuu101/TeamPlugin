package net.dxzzz.team;

import net.dxzzz.team.Database.DatabaseManager_Team;
import net.dxzzz.team.GUI.TeamGuiManager;
import net.dxzzz.team.Listener.PluginTeamMessage;
import net.dxzzz.team.ScheduleTask.CheckInvTask;
import net.dxzzz.team.Utils.UniversalModule;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Function {
    public static void SendMessage_WrongArgs(Player player) {
        player.sendMessage("§b§l组队系统>> §c您输入的参数有误=w=");
    }

    public static void SendMessage_WrongTeamName(Player player) {
        player.sendMessage("§b§l组队系统>> §c队伍名字格式错误,允许2-5个字,中文英文数字！");
    }

    public static void SendMessage_TeamExist(Player player) {
        player.sendMessage("§b§l组队系统>> §4创建队伍失败，已存在相同名称的队伍！");
    }

    public static void SendMessage_NoTeam_Chat(Player player) {
        player.sendMessage("§b§l组队系统>> §c你当前没有在任何队伍中，无法队伍聊天!");
    }

    public static void SendMessage_NoTeam_Ope(Player player) {
        player.sendMessage("§b§l组队系统>> §4你现在没有在任何队伍中");
    }

    public static void SendMessage_CreatTeamSuccessfully(Player player) {
        player.sendMessage(
                "§3§l⚎⚎⚎⚎⚎⚎⚎⚎ §6Dxzzz.Net §f- §9组队系统 §3§l⚎⚎⚎⚎⚎⚎⚎⚎\n" +
                        "§3 > §c创建队伍成功！\n" +
                        "§3 > §a你可以使用 §e/zd yq <玩家名> §a邀请别人加入\n" +
                        "§3§l⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎");

    }

    public static void SendMessage_QuitTeam(Player player) {
        player.sendMessage("§b§l组队系统>> §4退出队伍成功");
    }

    public static void SendMessage_HasTeam(Player player) {
        player.sendMessage("§b§l组队系统>> §c你已经在一个队伍当中了");
    }

    public static void SendMessage_HasInTeam(Player player) {
        player.sendMessage("§b§l组队系统>> §c该玩家已经在你的队伍当中了");
    }

    public static void SendMessage_NotCaptain(Player player) {
        player.sendMessage("§b§l组队系统>> §4你不是队长，无法操作");
    }

    public static void SendMessage_notInYourTeam(Player player) {
        player.sendMessage("§b§l组队系统>> §c这个玩家不在你的小队当中");
    }

    public static void SendMessage_TransferSuccess(Player player) {
        player.sendMessage("§b§l组队系统>> §e队长转让成功！");
    }

    public static void SendMessage_SwitchTeamChat(Player player) {
        TextComponent msg1 = new TextComponent("§b§l组队系统>> ");
        TextComponent msg2 = new TextComponent("§e[§e§n点击开启队伍频道聊天§e]");
        msg2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("开启后消息将被默认发送到队伍频道\n+§e也可输入/zd lt来切换默认队伍聊天").create()));
        msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/zd lt"));
        msg1.addExtra(msg2);
        player.spigot().sendMessage(msg1);
        return;
    }


    public static void SendMessage_TeamChatStatus(Player player, boolean isOpen) {
        if (isOpen) {
            player.sendMessage("§b§l组队系统>> §a默认队伍频道已开启");
        } else {
            player.sendMessage("§b§l组队系统>> §c默认队伍频道已关闭");
        }
    }

    public static void SendMessage_ShowTeamInfo(Player player, String teamName, String captain, String num1, String num2, String menbers) {
        String info =
                "§3§l⚎⚎⚎⚎⚎⚎⚎⚎ §6Dxzzz.Net §f- §9组队系统 §3§l⚎⚎⚎⚎⚎⚎⚎⚎\n" +
                        "§3 > §e队伍名称: §a%s\n" +
                        "§3 > §e队长: §a%s\n" +
                        "§3 > §e人数: §a%s/%s\n" +
                        "§3 > §e队员: §a%s \n" +
                        "§3§l⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎";
        String reslut = String.format(info, teamName, captain, num1, num2, menbers);
        player.sendMessage(reslut);
    }

    public static void SendMessage_AutoCreateTeam(Player player) {
        player.sendMessage("§b§l组队系统>> §e因为你没有在一个队伍中，所以系统自动为你创建了一个队伍！");
    }

    public static void SendMessage_PlayerNotOnline(Player player) {
        player.sendMessage("§b§l组队系统>> §4这个玩家不在线，无法邀请！");
    }

    public static void SendMessage_InviteSuccess(Player player) {
        player.sendMessage("§b§l组队系统>> §a邀请成功，等待对方接受§c(60秒内)§a！");
    }

    public static void SendMessage_NoInvitationFound(Player player) {
        player.sendMessage("§b§l组队系统>> §4没有找到邀请请求");
    }

    public static void SendMessage_MaxPlayer(Player player) {
        player.sendMessage("§b§l组队系统>> §4当前已达到队伍最大人数限制！");
    }
    public static void SendMessage_MaxPlayer_Captain(Player player) {
        player.sendMessage("§b§l组队系统>> §4队伍已经达到了最大队员数量！");
    }


    public static void SendMessage_CantCombine(Player player) {
        player.sendMessage("§b§l组队系统>> §4对方队伍人和我方队伍总人数过多，无法融合!");
    }

    public static void SendMessage_CantInviteSelf(Player player) {
        player.sendMessage("§b§l组队系统>> §4你不能邀请你自己!");
    }

    public static void SendMessage_haveSentInvitation(Player player) {
        player.sendMessage("§b§l组队系统>> §4你已经邀请过这个玩家了!");
    }



    public static void SendMessage_JoinTeam(Player player, String teamName) {
        String rawMsg =
                "§3§l⚎⚎⚎⚎⚎⚎⚎⚎ §6Dxzzz.Net §f- §9组队系统 §3§l⚎⚎⚎⚎⚎⚎⚎⚎\n" +
                        "§3 > §e你加入了 §6%s\n" +
                        "§3 > §e可以输入 §c/zd tc §e来退出小队，消息开头 §c# §e队伍聊天\n" +
                        "§3 > §c目前你的传送操作已归于队长管理，请勿操作！\n" +
                        "§3§l⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎⚎";
        String reslut = String.format(rawMsg, teamName);
        player.sendMessage(reslut);
    }


    public static boolean CreateTeam(Player player, String teamName, JavaPlugin plugin) {
        String name = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        List<String> playerInfo = databaseManager.GetPlayerInfo(name);
        if (playerInfo != null) {
            SendMessage_HasTeam(player);
            return false;
        }
        if (!UniversalModule.isNameValid(teamName)) {
            SendMessage_WrongTeamName(player);
            return false;
        }
        List <String> existingIDs = Team.GetDatabaseManager().GetAllTeamNames();
        if(existingIDs.contains(teamName)){
            SendMessage_TeamExist(player);
            return false;
        }
        SendMessage_CreatTeamSuccessfully(player);
        SendMessage_SwitchTeamChat(player);
        List<String> members_L = new ArrayList<>();
        String members_S = UniversalModule.ListToString(members_L);
        new BukkitRunnable() {
            @Override
            public void run() {
                databaseManager.AddTeamInfo(teamName, 1, name, members_S);
                databaseManager.AddPlayerInfo(name, teamName, "队长");
                return;
            }
        }.runTaskAsynchronously(plugin);
        return true;
    }


    public static boolean CreateTeam(Player player, JavaPlugin plugin) {
        String name = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        List<String> playerInfo = databaseManager.GetPlayerInfo(name);
        if (playerInfo != null) {
            SendMessage_HasTeam(player);
            return false;
        }
        SendMessage_CreatTeamSuccessfully(player);
        SendMessage_SwitchTeamChat(player);
        List<String> members_L = new ArrayList<>();
        String members_S = UniversalModule.ListToString(members_L);
        new BukkitRunnable() {
            @Override
            public void run() {
                String teamName = UniversalModule.generateUniqueID();
                databaseManager.AddTeamInfo(teamName, 1, name, members_S);
                databaseManager.AddPlayerInfo(name, teamName, "队长");
                return;
            }
        }.runTaskAsynchronously(plugin);
        return true;
    }

    public static void ShowTeamInfo(Player player, JavaPlugin plugin) {
        String username = player.getName();
        new BukkitRunnable() {
            @Override
            public void run() {
                DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_ShowTeamInfo(player, "无", "-", "- ", " -", "-");
                            return;
                        }
                    }.runTask(plugin);
                    return;
                } else {
                    String teamName = playerInfo.get(0);
                    List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName);
                    String num = teamInfo.get(0);
                    String captain = teamInfo.get(1);
                    if (Integer.parseInt(num) == 1) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_ShowTeamInfo(player, teamName, captain, num, String.valueOf(Team.GetMaxPlayers()), "-");
                                return;
                            }
                        }.runTask(plugin);
                        return;
                    } else {
                        List<String> members_L = databaseManager.GetTeamMembers(teamName);
                        String members_S = String.join(",", members_L);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_ShowTeamInfo(player, teamName, captain, num, String.valueOf(Team.GetMaxPlayers()), members_S);
                                return;
                            }
                        }.runTask(plugin);
                        return;
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public static void SwitchTeamChat(Player player, JavaPlugin plugin) {
        String username = player.getName();
        new BukkitRunnable() {
            @Override
            public void run() {
                DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NoTeam_Chat(player);
                            return;
                        }
                    }.runTask(plugin);
                    return;
                } else {
                    String teamChat = playerInfo.get(2);
                    if (teamChat.equals("0")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_TeamChatStatus(player, true);
                                return;
                            }
                        }.runTask(plugin);
                        databaseManager.SetPlayerTeamChat(username, 1);
                        return;
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_TeamChatStatus(player, false);
                                return;
                            }
                        }.runTask(plugin);
                        databaseManager.SetPlayerTeamChat(username, 0);
                        return;
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public static void SendTeamMsg(Player player, String msg, List<String> allMembers, JavaPlugin plugin) {
        for (String members : allMembers) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Message");
                out.writeUTF(members);
                out.writeUTF(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
        }
    }

    public static void QuitTeam(Player player, JavaPlugin plugin) {
        String username = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NoTeam_Ope(player);
                            return;
                        }
                    }.runTask(plugin);
                    return;
                } else {
                    if (playerInfo.get(1).equals("队长")) {
                        String teamName = playerInfo.get(0);
                        List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                String teamMsg = "§b§l组队系统>> §c你所在的队伍已解散";
                                SendTeamMsg(player, teamMsg, allMembers, plugin);
                                return;
                            }
                        }.runTask(plugin);
                        databaseManager.RemoveTeam(teamName);
                        for (String member : allMembers) {
                            List<String> teammates = new ArrayList<>(allMembers);
                            teammates.remove(member);
                            String teammates_S = UniversalModule.ListToString(teammates);
                            databaseManager.RemovePlayer(member);
                            databaseManager.UpdateRecentInfo(member,teammates_S);
                        }
                        databaseManager.RemoveInviteInfo_ByUserName(username);
                    } else {
                        String teamName = playerInfo.get(0);
                        List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                        allMembers.remove(username);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_QuitTeam(player);
                                String teamMsg = "§b§l组队系统>> §c" + username + " §4退出了队伍！";
                                SendTeamMsg(player, teamMsg, allMembers, plugin);
                                return;
                            }
                        }.runTask(plugin);
                        List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName);
                        String members_S = UniversalModule.ListToString(allMembers);
                        databaseManager.UpdateRecentInfo(username,members_S);
                        allMembers.remove(teamInfo.get(1));
                        members_S = UniversalModule.ListToString(allMembers);
                        int num = Integer.parseInt(teamInfo.get(0)) - 1;
                        databaseManager.SetTeamInfo(teamName, num, teamInfo.get(1), members_S);
                        databaseManager.RemovePlayer(username);

                    }
                    return;
                }

            }
        }.runTaskAsynchronously(plugin);

    }

    public static void KickMember(Player player, String aim_kick, JavaPlugin plugin) {
        String username = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NoTeam_Ope(player);
                            return;
                        }
                    }.runTask(plugin);
                    return;
                } else {
                    if (!playerInfo.get(1).equals("队长")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_NotCaptain(player);
                                return;
                            }
                        }.runTask(plugin);
                    } else {
                        String teamName = playerInfo.get(0);
                        List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                        if (!allMembers.contains(aim_kick)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Function.SendMessage_notInYourTeam(player);
                                    return;
                                }
                            }.runTask(plugin);
                            return;
                        }
                        String teamMsg = "§b§l组队系统>> §e队长 §a" + username + " §e踢出了玩家 §a" + aim_kick;
                        CountDownLatch latch = new CountDownLatch(1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendTeamMsg(player, teamMsg, allMembers, plugin);
                                latch.countDown();
                            }
                        }.runTask(plugin);
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (aim_kick.equals(username)) {
                            databaseManager.RemoveTeam(teamName);
                            for (String member : allMembers) {
                                databaseManager.RemovePlayer(member);
                                List<String> teammates = new ArrayList<>(allMembers);
                                teammates.remove(member);
                                String teammates_S = UniversalModule.ListToString(teammates);
                                databaseManager.UpdateRecentInfo(member,teammates_S);
                            }
                            databaseManager.RemoveInviteInfo_ByUserName(aim_kick);
                        } else {
                            List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName);
                            allMembers.remove(aim_kick);
                            String teammates_S = UniversalModule.ListToString(allMembers);
                            allMembers.remove(username);
                            String members_S = UniversalModule.ListToString(allMembers);
                            int num = Integer.parseInt(teamInfo.get(0)) - 1;
                            databaseManager.RemovePlayer(aim_kick);
                            databaseManager.SetTeamInfo(teamName, num, username, members_S);
                            databaseManager.UpdateRecentInfo(aim_kick,teammates_S);
                        }

                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }




    public static void TransferTeam(Player player,String newCaptain,JavaPlugin plugin) {
        String username = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NoTeam_Ope(player);
                            return;
                        }
                    }.runTask(plugin);
                    return;
                } else {
                    if (!playerInfo.get(1).equals("队长")) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_NotCaptain(player);
                                return;
                            }
                        }.runTask(plugin);
                    } else {
                        String teamName = playerInfo.get(0);
                        List<String> allMembers = databaseManager.GetAllTeamMembers(teamName);
                        if (!allMembers.contains(newCaptain)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Function.SendMessage_notInYourTeam(player);
                                    return;
                                }
                            }.runTask(plugin);
                            return;
                        }
                        String teamMsg = "§b§l组队系统>> §a" + newCaptain + " §e成为了新队长！";
                        CountDownLatch latch = new CountDownLatch(1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_TransferSuccess(player);
                                SendTeamMsg(player, teamMsg, allMembers, plugin);
                                latch.countDown();
                            }
                        }.runTask(plugin);
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName);
                        allMembers.remove(newCaptain);
                        String members_S = UniversalModule.ListToString(allMembers);
                        int num = Integer.parseInt(teamInfo.get(0));
                        databaseManager.SetTeamInfo(teamName, num, newCaptain, members_S);
                        databaseManager.SetPlayerInfo(username,teamName,"队员",0);
                        databaseManager.SetPlayerInfo(newCaptain,teamName,"队长",0);
                    }
                    return;
                }
            }
        }.runTaskAsynchronously(plugin);
    }


    public static void InvitePlayer(Player player,String guest_name,JavaPlugin plugin){
        String username = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run () {
                List<String> playerInfo =  databaseManager.GetPlayerInfo(username);
                if(playerInfo==null) {
                    CountDownLatch latch = new CountDownLatch(1);
                    List<String> members_L = new ArrayList<>();
                    String members_S = UniversalModule.ListToString(members_L);
                    String teamName = UniversalModule.generateUniqueID();
                    databaseManager.AddTeamInfo(teamName, 1, username, members_S);
                    databaseManager.AddPlayerInfo(username, teamName, "队长");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_AutoCreateTeam(player);
                            latch.countDown();
                        }
                    }.runTask(plugin);
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                playerInfo = databaseManager.GetPlayerInfo(username);
                String teamName_captain =playerInfo.get(0);
                List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName_captain);
                if(!playerInfo.get(1).equals("队长")){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NotCaptain(player);
                        }
                    }.runTask(plugin);
                    return;
                }
                else {
                    if(username.equals(guest_name)){
                        SendMessage_CantInviteSelf(player);
                        return;
                    }

                    int num_team_captain = Integer.parseInt(teamInfo.get(0));
                    if(num_team_captain>=Team.GetMaxPlayers()){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_MaxPlayer(player);
                                return;
                            }
                        }.runTask(plugin);
                        return;
                    }
                    if(!PluginTeamMessage.GetOnlinePlayers().contains(guest_name)){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_PlayerNotOnline(player);
                                return;
                            }
                        }.runTask(plugin);
                        return;
                    }
                    List<String> playerInfo_guest = databaseManager.GetPlayerInfo(guest_name);
                    if(playerInfo_guest!=null){
                        if (playerInfo_guest.get(1).equals("队长")){
                            String teamName_guest = playerInfo.get(0);
                            List<String> teamInfo_guest = databaseManager.GetTeamBasicInfo(teamName_guest);
                            int num_team_guest =Integer.parseInt( teamInfo_guest.get(0));
                            if(num_team_guest+num_team_captain>Team.GetMaxPlayers()) {
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        SendMessage_CantCombine(player);
                                        return;
                                    }
                                }.runTask(plugin);
                                return;
                            }
                        }
                        else if (playerInfo_guest.get(1).equals("队员")){
                            String teamName_guest = playerInfo.get(0);
                            if(teamName_guest.equals(teamName_captain)){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        SendMessage_HasInTeam(player);
                                        return;
                                    }
                                }.runTask(plugin);
                                return;
                            }
                        }
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_InviteSuccess(player);
                            ByteArrayOutputStream b = new ByteArrayOutputStream();
                            DataOutputStream out = new DataOutputStream(b);
                            try {
                                out.writeUTF("TeamChannel_InviteMsg");
                                out.writeUTF(username);
                                out.writeUTF(guest_name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
                            return;
                        }
                    }.runTask(plugin);
                    int id= databaseManager.GetInviteId(username,guest_name);
                    long curTime = System.currentTimeMillis();
                    if(id==-1){
                        databaseManager.AddInviteInfo(username,guest_name,curTime);
                    }
                    else {
                        databaseManager.SetInviteTime(id,curTime);
                    }
                    CheckInvTask.addElement();
                }
            }

        }.runTaskAsynchronously(plugin);
    }

    public static void AcceptInvite(Player player,String captain_name,JavaPlugin plugin){
        String guest_name = player.getName();
        DatabaseManager_Team databaseManager = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run() {
                int id = databaseManager.GetInviteId(captain_name,guest_name);
                if(id==-1){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NoInvitationFound(player);
                        }
                    }.runTask(plugin);
                }
                else {
                    List<String> playerInfo_guest = databaseManager.GetPlayerInfo(guest_name);
                    List<String> playerInfo_captain= databaseManager.GetPlayerInfo(captain_name);
                    String teamName_captain = playerInfo_captain.get(0);
                    List<String> teamInfo_captain = databaseManager.GetTeamBasicInfo(teamName_captain);
                    int num_captain = Integer.parseInt(teamInfo_captain.get(0)  );
                    List<String> members_captain = databaseManager.GetAllTeamMembers(teamName_captain);
                    if(playerInfo_guest!=null){
                        String title_guest = playerInfo_guest.get(1);
                        String teamName_guest = playerInfo_guest.get(0);
                        if(title_guest.equals("队长")){
                            List<String> teamInfo_guest = databaseManager.GetTeamBasicInfo(teamName_guest);
                            int num_guest =Integer.parseInt( teamInfo_guest.get(0));
                            if(num_captain+num_guest>Team.GetMaxPlayers()){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        SendMessage_CantCombine(player);
                                    }
                                }.runTask(plugin);
                                databaseManager.RemoveInviteInfo(id);
                                return;
                            }
                            List<String> allMembers_guest = databaseManager.GetAllTeamMembers(teamName_guest);
                            CountDownLatch latch = new CountDownLatch(1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    String combineMsg = "§b§l组队系统>> §e你的小队与 §a"+captain_name+" §e的 §6"+teamName_captain+" §e小队融合了！";
                                    String joinList =String.join(",",allMembers_guest);
                                    String joinMsg = "§b§l组队系统>> §a"+joinList+" §e加入了队伍！";
                                    SendTeamMsg(player,combineMsg,allMembers_guest,plugin);
                                    SendTeamMsg(player,joinMsg,members_captain,plugin);
                                    latch.countDown();
                                }
                            }.runTask(plugin);
                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            int num_team_guest = allMembers_guest.size();
                            members_captain.remove(captain_name);
                            for(String member: allMembers_guest){
                                members_captain.add(member);
                                databaseManager.SetPlayerInfo(member,teamName_captain,"队员",0);
                            }
                            String members_captain_S = UniversalModule.ListToString(members_captain);
                            databaseManager.SetTeamInfo(teamName_captain,num_team_guest+Integer.parseInt( teamInfo_captain.get(0)),captain_name,members_captain_S);
                            databaseManager.RemoveTeam(teamName_guest);
                        }
                        else{
                            if(num_captain>=Team.GetMaxPlayers()){
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        SendMessage_MaxPlayer_Captain(player);
                                    }
                                }.runTask(plugin);
                                databaseManager.RemoveInviteInfo(id);
                                return;
                            }
                            List<String> allMembers_guest = databaseManager.GetAllTeamMembers(teamName_guest);
                            allMembers_guest.remove(guest_name);
                            CountDownLatch latch = new CountDownLatch(1);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    String joinMsg ="§b§l组队系统>> §a"+guest_name+" §e加入了队伍！";
                                    String quitMsg ="§b§l组队系统>> §c"+guest_name+" §4退出了队伍！";
                                    SendMessage_JoinTeam(player,teamName_captain);
                                    SendMessage_SwitchTeamChat(player);
                                    SendTeamMsg(player,joinMsg,members_captain,plugin);
                                    SendTeamMsg(player,quitMsg,allMembers_guest,plugin);
                                    latch.countDown();
                                }
                            }.runTask(plugin);
                            try {
                                latch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            members_captain.add(guest_name);
                            members_captain.remove(captain_name);
                            allMembers_guest.remove(guest_name);
                            List<String> teamInfo_guest = databaseManager.GetTeamBasicInfo(teamName_guest);
                            String members_captain_S = UniversalModule.ListToString(members_captain);
                            String members_guest_S = UniversalModule.ListToString(allMembers_guest);
                            databaseManager.SetTeamInfo(teamName_captain,1+Integer.parseInt( teamInfo_captain.get(0)),captain_name,members_captain_S);
                            databaseManager.SetTeamInfo(teamName_guest,Integer.parseInt(teamInfo_guest.get(0))-1,teamInfo_guest.get(1),members_guest_S);
                            databaseManager.SetPlayerInfo(guest_name,teamName_captain,"队员",0);
                        }
                    }
                    else {
                        if(num_captain>=Team.GetMaxPlayers()){
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    SendMessage_MaxPlayer_Captain(player);
                                }
                            }.runTask(plugin);
                            databaseManager.RemoveInviteInfo(id);
                            return;
                        }
                        CountDownLatch latch = new CountDownLatch(1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                String joinMsg ="§b§l组队系统>> §a"+guest_name+" §e加入了队伍！";
                                SendMessage_JoinTeam(player,teamName_captain);
                                SendMessage_SwitchTeamChat(player);
                                SendTeamMsg(player,joinMsg,members_captain,plugin);
                                latch.countDown();
                            }
                        }.runTask(plugin);
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        members_captain.add(guest_name);
                        members_captain.remove(captain_name);
                        String members_captain_S = UniversalModule.ListToString(members_captain);
                        databaseManager.SetTeamInfo(teamName_captain,1+Integer.parseInt( teamInfo_captain.get(0)),captain_name,members_captain_S);
                        databaseManager.AddPlayerInfo(guest_name,teamName_captain,"队员");
                    }
                    databaseManager.RemoveInviteInfo(id);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public static void InviteRecentPlayers(Player player,JavaPlugin plugin){
        String username = player.getName();
        DatabaseManager_Team databaseManager  = Team.GetDatabaseManager();
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> playerInfo = databaseManager.GetPlayerInfo(username);
                if (playerInfo == null) {
                    CountDownLatch latch = new CountDownLatch(1);
                    List<String> members_L = new ArrayList<>();
                    String members_S = UniversalModule.ListToString(members_L);
                    String teamName = UniversalModule.generateUniqueID();
                    databaseManager.AddTeamInfo(teamName, 1, username, members_S);
                    databaseManager.AddPlayerInfo(username, teamName, "队长");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_AutoCreateTeam(player);
                            latch.countDown();
                        }
                    }.runTask(plugin);
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                playerInfo = databaseManager.GetPlayerInfo(username);
                if(!playerInfo.get(1).equals("队长")){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMessage_NotCaptain(player);
                        }
                    }.runTask(plugin);
                    return;
                }
                else {
                    List<String> teammates = databaseManager.GetRecentInfo(username);
                    String teamName = playerInfo.get(0);
                    List<String> teamInfo = databaseManager.GetTeamBasicInfo(teamName);
                    if(Integer.parseInt(teamInfo.get(0))>=Team.GetMaxPlayers()){
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SendMessage_MaxPlayer(player);
                            }
                        }.runTask(plugin);
                        return;
                    }
                    if (teammates.isEmpty() || teammates == null) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage("§b§l组队系统>> §c最近没有和其他人组队哦~");
                            }
                        }.runTask(plugin);
                        return;
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (String teammate : teammates) {
                                    player.sendMessage("§e正在邀请: " + teammate + "…");
                                    InvitePlayer(player, teammate, plugin);
                                }
                            }
                        }.runTask(plugin);
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public static void OpenGuiForPlayer (Player player) {
        TeamGuiManager teamGuiManager = new TeamGuiManager(player);
        teamGuiManager.openGui();
    }
}
