package net.dxzzz.team.Listener;

import net.dxzzz.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class TeamFastInviteListener implements Listener {
    @EventHandler
    public void onPlayerRightClickPlayer(PlayerInteractEntityEvent event) {
        if(Team.GetAllowFastInvite()) {
            Player player = event.getPlayer();
            if (event.getRightClicked() instanceof Player) {
                Player guest = (Player) event.getRightClicked();
                if (player.isSneaking()) {
                    player.performCommand("zd yq " + guest.getName());
                }
            }
        }
    }


}
