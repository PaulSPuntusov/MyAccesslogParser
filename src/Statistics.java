import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;

public class Statistics {
    static long totalTraffic=0;
    static LocalDateTime minTime,maxTime;
    static HashSet<String> siteExist = new HashSet<>();
    public Statistics() {
    }
    public static void addEntry(LogEntry le){
       totalTraffic += Long.parseLong(le.referer);
       if (minTime.compareTo(le.time)>0){minTime=le.time;};
       if (le.time.compareTo(maxTime)>0){maxTime=le.time;}
       if(le.responseCode==200){
           siteExist.add(le.path);
       }

    }

    public static long getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }
    public static long getTrafficRate(){
        long res;
        res = Duration.between(minTime,maxTime).toHours();
        res = totalTraffic/res;
        return res;
    }
    public static void siteList(){
        System.out.println(siteExist);
    }

}
