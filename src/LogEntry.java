import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class LogEntry {
    java.util.Locale locale = Locale.US;
    public final String line;
    public final String ipAddr;
    public final LocalDateTime time;

    public LogEntry(String line) {
        this(line, "", null);
    }

    public LogEntry(String line, String ipAddr, LocalDateTime time) {
        this.line = line;
        this.ipAddr = ip();
        this.time = ldt();
    }


    // парсим IP
    public String ip() {
        Pattern p = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
        Matcher m = p.matcher(this.line);
        return m.find() ? m.group() : "Not found";
    }

    // парсим дату_время по квадратным скабкам
    public LocalDateTime ldt() {
        String date_time;
        LocalDateTime dl;
        Pattern p = Pattern.compile("[\\[](.*?)[\\]]"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
        Matcher m = p.matcher(this.line);
        date_time = m.find() ? m.group() : "Not found";
        date_time = date_time.replace(" +0300", "");
        date_time = date_time.replace("[", "");
        date_time = date_time.replace("]", "");
        date_time = date_time.replace("/", ",");
        //date_time = "25,Sep,2000:20:20:20";
        //System.out.println(date_time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MMM,yyyy:HH:mm:ss",locale);
        dl = LocalDateTime.parse(date_time, formatter);
        return dl;
    }

}
