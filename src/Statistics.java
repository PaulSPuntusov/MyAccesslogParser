import java.time.LocalDateTime;

public class Statistics {
    static int totalTraffic=0;
    static LocalDateTime minTime,maxTime;
    public Statistics() {
    }
    public void addEntry(LogEntry le){
       totalTraffic += Integer.parseInt(le.referer);
       if (minTime.isAfter(le.time)){minTime=le.time;};
       if (le.time.isAfter(maxTime)){maxTime=le.time;}
    }

    public int getTotalTraffic() {
        return totalTraffic;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

}
