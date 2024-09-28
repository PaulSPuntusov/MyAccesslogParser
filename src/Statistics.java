import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statistics {
    static int count = 0; // счетчик операционных систем
    static int oldtime = 0; // счетчик начального времени
    static int i = 1; // счетчик запросов в секунду
    static long totalTraffic = 0; //счетчик общего траффика
    static long trafficNoBots = 0; //счетчик траффика без ботов
    static long failRequestCount = 0; // счетчик траффика с возвращенной ошибкой
    static int osTotalCount = 0; // счетчик общего количества операционных систем
    static int browserTotalCount = 0; // счетчик браузеров
    static int attendance = 0; // счетчик количества посещений не ботами
    static LocalDateTime minTime, maxTime;
    static HashSet<String> siteExist = new HashSet<>(); // возвращает набор страниц
    static HashMap<String, Integer> osStatistics = new HashMap<>(); // возвращает набор операционных систем
    static HashMap<String, Double> osTotalStatistics = new HashMap<>(); // возвращает набор операционных систем и их статистику
    static HashSet<String> siteNotExist = new HashSet<>(); // возвращает список несуществующих страниц
    static HashMap<String, Integer> browserStatistics = new HashMap<>(); // возвращает набор браузеров
    static HashMap<String, Double> browserTotalStatistics = new HashMap<>(); //возвращает нвбор браузеров в % содержании
    static HashSet<String> uniqIp = new HashSet<>(); // возвращает уникальные IP
    static HashMap<Integer, Integer> attendanceStatistics = new HashMap<>(); // возвращает посекундную статистику посещений
    static HashSet<String> sites = new HashSet<>(); // собираем сайты в кучку

    public Statistics() {
    }

    public static void addEntry(LogEntry le) {

        totalTraffic += Long.parseLong(le.referer); //считаем общий траффик
        if (!le.userAgent.isBot()) {
            trafficNoBots += Long.parseLong(le.referer); //считаем траффик без ботов
            uniqIp.add(le.ipAddr); //собираем уникальные IP адреса (не боты)
            attendance++;
            int time = le.time.getDayOfYear() * 24 * 3600 + le.time.getHour() * 3600 + le.time.getMinute() * 60 + le.time.getSecond();// счетчик времени
            if (time == oldtime) {
                attendanceStatistics.put(time, i++);
                //System.out.println(time+" "+i);
            }
            if (time != oldtime) {
                i = 1;
                oldtime = time;
                attendanceStatistics.put(time, i);
                //System.out.println(time+" "+i);
            }
        }
        if (le.responseCode > 399) {
            failRequestCount++; //считаем количество возвращенных ошибок
        }
        if (minTime.compareTo(le.time) > 0) {
            minTime = le.time;
        }
        ;
        if (le.time.compareTo(maxTime) > 0) {
            maxTime = le.time;
        }
        if (le.responseCode == 200) {
            siteExist.add(le.path);
            Pattern p = Pattern.compile("[(\\\\](.*?)[)\\\\]"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
            Matcher m = p.matcher(le.parts[10]);
            System.out.println(m.find() ? m.group() : "Not found");
            sites.add(le.parts[10]);
        }
        if (le.responseCode == 404) {
            siteNotExist.add(le.path);
        }
        if (osStatistics.containsKey(le.userAgent.os)) {
            count = osStatistics.get(le.userAgent.os);
            osStatistics.replace(le.userAgent.os, ++count);
            osTotalCount++;
            //System.out.println("% "+((double)count/totalCount));
            osTotalStatistics.replace(le.userAgent.os, ((double) count / osTotalCount));
            //System.out.println(osStatistics);
            //System.out.println(osTotalStatistics);
        }
        if (!osStatistics.containsKey(le.userAgent.os)) {
            osStatistics.put(le.userAgent.os, 1);
            osTotalCount++;
            osTotalStatistics.put(le.userAgent.os, ((double) count / osTotalCount));
        }
        getBrowserTotalStatistics(le.userAgent.browser);

    }

    public static HashMap<String, Double> getBrowserTotalStatistics(String le) {
        int browserCount = 0;
        if (browserStatistics.containsKey(le)) {
            browserCount = browserStatistics.get(le);
            browserStatistics.replace(le, ++browserCount);
            browserTotalCount++;
            //System.out.println("browserCount "+((double)browserCount));
            //System.out.println("browserTotalCount "+(browserTotalCount));
            //System.out.println("/ "+((double)browserCount/browserTotalCount));
            browserTotalStatistics.replace(le, ((double) browserCount / browserTotalCount));
            //System.out.println(browserStatistics);
            //System.out.println(browserTotalStatistics);
        }
        if (!browserStatistics.containsKey(le)) {
            browserStatistics.put(le, 1);
            browserTotalCount++;
            browserTotalStatistics.put(le, ((double) browserCount / browserTotalCount));
        }
        return browserTotalStatistics;
    }

    public static long getTotalTraffic() {
        return totalTraffic; // считаем общий траффик
    }

    public static long duration() {
        return (Duration.between(minTime, maxTime).toHours()); //считаем общее время лога
    }


    public static long getTrafficRate() {
        return (totalTraffic / duration()); //возвращаем средний траффик
    }

    public static long getTrafficRateNoBot() {
        return (attendance / duration()); //возвращаем средний траффик без ботов
    }

    public static double getFailRequestRate() {
        System.out.println("Количество упавших запросов " + failRequestCount + "/" + "Время " + duration());
        return (failRequestCount / duration()); // возвращаем среднее количество ошибок за час
    }

    public static long getAttendance() {
        return (attendance / uniqIp.size()); //средняя посещаемость одним пользователем
    }

    public static Integer peakAttendance() {
        return attendanceStatistics.values().stream().max(Integer::compare).get();
    }
}
