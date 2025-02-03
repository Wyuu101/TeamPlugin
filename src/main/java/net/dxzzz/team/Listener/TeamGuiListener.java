package net.dxzzz.team.Listener;

//import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUI;
import net.dxzzz.team.Function;
import net.dxzzz.team.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

public class TeamGuiListener implements Listener {
    private final JavaPlugin plugin;
    public TeamGuiListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().equals("§6Dxz§ezz.§bNet §c>> §4组队")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if(slot >=20&&slot <=26) {
                ItemStack item = event.getInventory().getItem(slot);
                if(item!=null&&item.getType()== Material.SKULL_ITEM) {
                    PlayerData playerData = new PlayerData(player.getName());
                    if(playerData.getIsInTeam()) {
                        String title = playerData.getTitle();
                        if (title.equals("队长")) {
                            player.performCommand("zd t "+item.getItemMeta().getDisplayName().substring(2));
                        }
                    }
                    else {
                        player.performCommand("zd yq "+item.getItemMeta().getDisplayName().substring(2));
                    }
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 5);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Function.OpenGuiForPlayer(player);
                        }
                    }.runTaskLater(plugin,5);
                    return;
                }
            }
            switch (slot){
                case 0:
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    break;

                case 2:
                    try {
                        SignGUI gui = SignGUI.builder()
                                .setLines(null, "请在第一行输入", "要邀请的玩家名")
                                .setHandler((p, result) -> {
                                    String name = result.getLine(0);
                                    Bukkit.getScheduler().runTask(plugin, () -> {
                                        player.performCommand("zd yq " + name);
                                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                                    });
                                    return Collections.emptyList();
                                })
                                .build();
                        gui.open(player);
                    }
                    catch (Exception e) {
                        return;
                    }
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    break;
                case 3:
                    player.performCommand("zd zj");
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                    break;
                case 8:
                    player.performCommand("zd tc");
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Function.OpenGuiForPlayer(player);
                            return;
                        }
                    }.runTaskLater(plugin,5);
                    break;
                case 18:
                    player.performCommand("zd lt");
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    break;
                case 53:
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "dgui openfor "+player.getName()+" gerencaidan.yml");
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                    break;
                default:
                    break;
            }
        }
    }
}
