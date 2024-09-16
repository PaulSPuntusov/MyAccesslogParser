import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    static long totalTraffic=0;
    static LocalDateTime minTime,maxTime;
    public Statistics() {
    }
    public static void addEntry(LogEntry le){
       totalTraffic += Long.parseLong(le.referer);
       if (minTime.compareTo(le.time)>0){minTime=le.time;};
       if (le.time.compareTo(maxTime)>0){maxTime=le.time;}
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

}
