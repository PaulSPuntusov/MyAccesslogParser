import java.time.LocalDateTime;

public class Statistics {
    static Double totalTraffic=0.;
    static LocalDateTime minTime,maxTime;
    public Statistics() {
    }
    public static void addEntry(LogEntry le){
       totalTraffic += Double.parseDouble(le.referer);
       if (minTime.compareTo(le.time)>0){minTime=le.time;};
       if (le.time.compareTo(maxTime)>0){maxTime=le.time;}
    }

    public static Double getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

}
