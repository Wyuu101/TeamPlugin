package net.dxzzz.team.Listener;

import de.marcely.bedwars.api.event.player.PlayerJoinArenaEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dxzzz.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
public class PlayerJoinGameListener implements Listener {
    private final JavaPlugin plugin;
    public PlayerJoinGameListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public static PlayerJoinGameListener instance;//
    public static PlayerJoinGameListener getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new PlayerJoinGameListener(plugin);
        }
        return instance;
    }
    @EventHandler
    private void onPlayerJoin(PlayerJoinArenaEvent event) {
        Player player = event.getPlayer();
        List<String> playerInfo = Team.GetDatabaseManager().GetPlayerInfo(player.getName());
        String title = playerInfo.get(1);
        if (title.equals("队长")) {
            List<String> members = Team.GetDatabaseManager().GetTeamMembers(playerInfo.get(0));
            for (String member : members) {
                if(plugin.getServer().getPlayer(member).isOnline()){
                    //如果玩家在本服,让玩家自己加入
//                    String command ="bw join " +PlaceholderAPI.setPlaceholders(player,"%best[status=2 & players_per_team=4]%");
                    String command ="bw join %best[status=2 & players_per_team=4]%";
                    Player memberPlayer = plugin.getServer().getPlayer(member);
                    memberPlayer.performCommand(command);
                }
                else{
                    //如果玩家不在本服,使用bc命令让他加入
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(b);
                    try {
                        out.writeUTF("ConnectOther");
                        out.writeUTF(member);
                        out.writeUTF(PlaceholderAPI.setPlaceholders(player, "%redis_online_%"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Player memberPlayer = plugin.getServer().getPlayer(member);
                            String command ="bw join %best[status=2 & players_per_team=4]%";
                            memberPlayer.performCommand(command);
                        }
                    }.runTaskLater(plugin,15);
                }
            }
        }
    }

}
