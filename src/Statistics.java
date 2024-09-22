import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    static long totalTraffic = 0;
    static int osTotalCount = 0; // счетчик общего количества операционных систем
    static int browserTotalCount = 0; // счетчик браузеров
    static LocalDateTime minTime, maxTime;
    static HashSet<String> siteExist = new HashSet<>(); // возвращает набор страниц
    static HashMap<String, Integer> osStatistics = new HashMap<>(); // возвращает набор операционных систем
    static HashMap<String,Double> osTotalStatistics = new HashMap<>(); // возвращает набор операционных систем и их статистику
    static HashSet<String> siteNotExist = new HashSet<>(); // возвращает список несуществующих страниц
    static HashMap<String ,Integer> browserStatistics = new HashMap<>(); // возвращает набор браузеров
    static HashMap<String,Double> browserTotalStatistics = new HashMap<>(); //возвращает нвбор браузеров в % содержании

    public Statistics() {
    }

    public static void addEntry(LogEntry le) {
        int count = 0; // счетчик операционных систем
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
        if (le.responseCode == 404){
            siteNotExist.add(le.path);
        }
            if(osStatistics.containsKey(le.userAgent.os)){
            count = osStatistics.get(le.userAgent.os);
            osStatistics.replace(le.userAgent.os,++count);
            osTotalCount++;
            //System.out.println("% "+((double)count/totalCount));
            osTotalStatistics.replace(le.userAgent.os,((double)count/ osTotalCount));
            //System.out.println(osStatistics);
            //System.out.println(osTotalStatistics);
        }
        if(!osStatistics.containsKey(le.userAgent.os)){
            osStatistics.put(le.userAgent.os,1);
            osTotalCount++;
            osTotalStatistics.put(le.userAgent.os,((double)count/ osTotalCount));
        }
        getBrowserTotalStatistics(le.userAgent.browser);

    }
    public static HashMap<String,Double> getBrowserTotalStatistics(String le){
        int browserCount = 0;
        if(browserStatistics.containsKey(le)){
            browserCount = browserStatistics.get(le);
            browserStatistics.replace(le,++browserCount);
            browserTotalCount++;
            //System.out.println("browserCount "+((double)browserCount));
            //System.out.println("browserTotalCount "+(browserTotalCount));
            //System.out.println("/ "+((double)browserCount/browserTotalCount));
            browserTotalStatistics.replace(le,((double)browserCount/browserTotalCount));
            //System.out.println(browserStatistics);
            //System.out.println(browserTotalStatistics);
        }
        if(!browserStatistics.containsKey(le)){
            browserStatistics.put(le,1);
            browserTotalCount++;
            browserTotalStatistics.put(le,((double)browserCount/browserTotalCount));
        }
        return browserTotalStatistics;
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
