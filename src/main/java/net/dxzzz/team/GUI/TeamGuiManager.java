package net.dxzzz.team.GUI;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import net.dxzzz.team.PlayerData;
import net.dxzzz.team.Utils.CustomItemHead;
import net.dxzzz.team.Utils.CustomItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TeamGuiManager {
    private Inventory gui = null;
    private Player player = null;
    public TeamGuiManager(Player player) {
        this.player = player;
        createGui(player);
    }
    private void createGui(Player player) {
        String playerName = player.getName();
        String guiTitle = "§6Dxz§ezz.§bNet §c>> §4组队";
        gui = Bukkit.createInventory(null, 54, guiTitle);

        //边界物品
        ItemStack border = new CustomItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7,"§7Dxzzz.Net");
        int[] borderIndex = {1,9,10,11,12,13,14,15,16,17,19,28,37,46};
        for (int index = 0; index < borderIndex.length; index++) {
            gui.setItem(borderIndex[index], border);
        }



        PlayerData playerData = new PlayerData(playerName);
        //玩家不在队伍
        if(!playerData.getIsInTeam()){
            //玩家自身头像
            List<String> selfHeadLore = new ArrayList<>();
            selfHeadLore.add("§c你没有在任何一个队伍中！");
            ItemStack selfHead = new CustomItemStack(Material.ENCHANTED_BOOK,1, (short) 0,"§e组队！",selfHeadLore);
            gui.setItem(0, selfHead);

            //历史成员头像
            List<String> historyMembers = playerData.getHistoryMembers();
            int indexOfHistoryMemberHead = 20;
            for(String historymemberName : historyMembers){
                List<String> memberHeadLore = new ArrayList<>();
                memberHeadLore.add("");
                memberHeadLore.add("§7最近组队");
                memberHeadLore.add("");
                memberHeadLore.add("§e点击发送组队邀请");
                ItemStack memberHead = new CustomItemHead(1,"§e"+historymemberName,memberHeadLore,historymemberName);
                gui.setItem(indexOfHistoryMemberHead, memberHead);
                indexOfHistoryMemberHead++;
            }

            //快速邀请按钮
            List<String> fastInviteButtonLore = new ArrayList<>();
            fastInviteButtonLore.add("§7将批量邀请最后一次进行组队的玩家");
            ItemStack fastInviteButton = new CustomItemHead("21769","§6快速邀请",fastInviteButtonLore);
            gui.setItem(3,fastInviteButton);


        }else{
            //玩家自身头像
            List<String> selfHeadLore = new ArrayList<>();
            selfHeadLore.add("§7队伍名字: §a"+playerData.getTeamName());
            selfHeadLore.add("§7队长: §a"+playerData.getCaptain());
            selfHeadLore.add("§7人数: §a"+ playerData.getNumOfMembers());
            ItemStack selfHead = new CustomItemStack(Material.ENCHANTED_BOOK,1, (short) 0,"§e组队！",selfHeadLore);
            gui.setItem(0, selfHead);

            //队伍成员头像
            List<String> memberNames = playerData.getAllMembers();
            String title = playerData.getTitle();
            int indexOfMemberHead = 20;
            for(String memberName : memberNames){
                List<String> memberHeadLore = new ArrayList<>();
                if(title.equals("队长")){
                    memberHeadLore.add("");
                    memberHeadLore.add("§7点击踢出该玩家");
                }
                ItemStack memberHead =new CustomItemHead(1,"§e"+memberName,memberHeadLore,memberName);
                gui.setItem(indexOfMemberHead, memberHead);
                indexOfMemberHead++;
            }

            //退出队伍按钮
            List<String> exitButtonLore = new ArrayList<>();
            exitButtonLore.add("§7点击退出当前队伍");
            exitButtonLore.add("");
            exitButtonLore.add("§c队长退出队伍将导致队伍解散");
            ItemStack exitButton = new CustomItemHead("9382", "§c退出队伍", exitButtonLore);
            gui.setItem(8, exitButton);

        }
        //组队按钮
        List<String> inviteButtonLore = new ArrayList<>();
        inviteButtonLore.add("§7点击邀请他人加入队伍");
        ItemStack inviteButton = new CustomItemHead("10209", "§a邀请玩家组队", inviteButtonLore);
        gui.setItem(2, inviteButton);

        //返回按钮
        ItemStack backButton = new CustomItemHead("9222", "§c✈返回",null);
        gui.setItem(53, backButton);

        //组队聊天切换按钮
        ItemStack teamChatButton = new CustomItemHead("9187", "§a开启/关闭组队聊天", null);
        gui.setItem(18, backButton);
    }

    public void openGui() {
        player.openInventory(gui);
    }

}
