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
    public final HttpMethod method;
    public final String path;

    public LogEntry(String line) {
        this(line, "", null,null,"");
    }

    public LogEntry(String line, String ipAddr, LocalDateTime time, HttpMethod method, String path) {
        this.line = line;
        this.method = meth();
        this.ipAddr = ip();
        this.time = ldt();
        this.path = path();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MMM,yyyy:HH:mm:ss",locale);
        dl = LocalDateTime.parse(date_time, formatter);
        return dl;
    }
    public HttpMethod meth() {
        String str;
        Pattern p = Pattern.compile("[\"](\\S+)"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
        Matcher m = p.matcher(this.line);
        str = m.find() ? m.group() : "Not found";
        switch (m.find() ? m.group() : "Not found"){
            case ("CONNECT"):
               return HttpMethod.CONNECT;
            case ("DELETE"):
                return HttpMethod.DELETE;
            case ("GET"):
                return HttpMethod.GET;
            case ("HEAD"):
                return HttpMethod.HEAD;
            case ("OPTIONS"):
                return HttpMethod.OPTIONS;
            case ("PUT"):
                return HttpMethod.PUT;
            case ("PATCH"):
                return HttpMethod.PATCH;
            case ("POST"):
                return HttpMethod.POST;
            case ("TRACE"):
                return HttpMethod.POST;
            default:
                return HttpMethod.GET;
        }
    }
    public String path(){
        Pattern p = Pattern.compile("(GET\r)"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
        Matcher m = p.matcher(this.line);
        return m.find() ? m.group() : "Not found";
    }

}
