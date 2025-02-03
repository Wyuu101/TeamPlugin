package net.dxzzz.team.ScheduleTask;

import net.dxzzz.team.Database.DatabaseManager_Team;
import net.dxzzz.team.Team;
import net.dxzzz.team.Utils.UniversalModule;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;


public class CheckInvTask {

    private static JavaPlugin plugin;
    private static DatabaseManager_Team databaseManager;

    public CheckInvTask(JavaPlugin plugin) {
        this.plugin = plugin;
        databaseManager= Team.GetDatabaseManager();
    }

    private static BukkitTask task;
    public static void startTask() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            int numOfInvite = databaseManager.GetNumOfInviteData();
            if (numOfInvite<=0) {
                stopTask();
            } else {
                CheckInviteFunc();
            }
        }, 0L, 100L);
    }

    public static void stopTask() {
        if (task != null) {
            task.cancel();
            task=null;
        }
    }

    public static void addElement() {
        if (task == null) {
            startTask();
        }
    }

    //邀请信息过期检查
    public static void CheckInviteFunc(){
        List<Integer> idList =databaseManager.GetIdList();
        List<Long> timeList = databaseManager.GetTimeList() ;
        List<Pair<Integer,Long>> TimeInfo = UniversalModule.MergeLists(idList,timeList);
        long curTime = System.currentTimeMillis();
        for(Pair<Integer,Long> pair :TimeInfo){
            long startTime= pair.getValue();
            if((curTime-startTime)>= 60000){
                databaseManager.RemoveInviteInfo(pair.getKey());
            }
        }
    }

}
