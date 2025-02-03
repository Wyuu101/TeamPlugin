package net.dxzzz.team.Listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.dxzzz.team.Utils.UniversalModule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.List;


public class PluginTeamMessage implements PluginMessageListener {
    private final JavaPlugin plugin;
    private static List<String> onlinePlayers = new ArrayList<>();

    public PluginTeamMessage(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equals("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subChannel = in.readUTF();
            if (subChannel.equals("OnlinePlayers")) {
                String onlinePlayersString = in.readUTF();
                onlinePlayers = UniversalModule.StringToList(onlinePlayersString);
                return;
            }
        }
    }

    public static List<String> GetOnlinePlayers(){
        return onlinePlayers;
    }
}


