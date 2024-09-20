import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    static long totalTraffic = 0;
    static LocalDateTime minTime, maxTime;
    static HashSet<String> siteExist = new HashSet<>(); // возвращает набор страниц
    static HashMap<String, Integer> osStatistics = new HashMap<>(); // возвращает набор операционных систем
    static HashMap<String,Double> osTotalStatistics = new HashMap<>(); // возвращает набор операционных систем и их статистику

    public Statistics() {
    }

    public static void addEntry(LogEntry le) {
        int count = 0; // счетчик операционных систем
        int totalCount = 0; // счетчик общего количества операционных систем
        totalTraffic += Long.parseLong(le.referer);
        if (minTime.compareTo(le.time) > 0) {
            minTime = le.time;
        }
        ;
        if (le.time.compareTo(maxTime) > 0) {
            maxTime = le.time;
        }
        if (le.responseCode == 200) {
            siteExist.add(le.path);
        }
        if(osStatistics.containsKey(le.userAgent.os)){
            count = osStatistics.get(le.userAgent.os);
            osStatistics.replace(le.userAgent.os,++count);
            totalCount++;
            osTotalStatistics.replace(le.userAgent.os,((double)count/totalCount));
            System.out.println(osStatistics);
            System.out.println(osTotalStatistics);
        }
        if(!osStatistics.containsKey(le.userAgent.os)){
            osStatistics.put(le.userAgent.os,1);
            totalCount++;
            osTotalStatistics.replace(le.userAgent.os,((double)count/totalCount));
        }


    }

    public static long getTotalTraffic() {
        return totalTraffic;
    }


    public static long getTrafficRate() {
        long res;
        res = Duration.between(minTime, maxTime).toHours();
        res = totalTraffic / res;
        return res;
    }

}
