import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;


public class LogEntry {
    java.util.Locale locale = Locale.US; // локаль для времени
    public final String line; // вся строка лога
    public final String ipAddr; // IP из строки
    public final LocalDateTime time; // Дата-время
    public final HttpMethod method;  // метод запроса
    public final String path; // путь запроса
    public  final int responseCode; // код ответа
    public final String referer; //объем ответа
    public final String[] parts; // разбиение строки на массив
    public final UserAgent userAgent; // операционная система и браузер

    public LogEntry(String line) {
        this(line, "", null,null,"", 0, "", null);
    }

    public LogEntry(String line, String ipAddr, LocalDateTime time, HttpMethod method, String path, int responseCode, String referer, UserAgent userAgent) {
        this.line = line;
        this.parts = line.split(" ");
        this.userAgent = userAgent();
        this.referer = referer();
        this.responseCode = responseCode();
        this.method = meth();
        this.ipAddr = ip();
        this.time = ldt();
        this.path = path();
    }


    // парсим IP реализован вариант и с регуляркой и просто чтение строки о словам
    public String ip() {
         Pattern p = Pattern.compile("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b");
         Matcher m = p.matcher(this.line);
        // return parts[0];
        return m.find() ? m.group() : "Not found";
    }

    // парсим дату_время по квадратным скобкам
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

    // распарсил метод
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
    //неудачная попытка распарсить путь
    public String path(){
        String str = parts[10]+parts[6];
        return str;
    }
    public int responseCode(){
        return parseInt(parts[8]);
    }
    public String referer(){
        return parts[9];
    }

    public UserAgent userAgent(){
        UserAgent ua = new UserAgent(this.line);
        //System.out.println(ua.getOs());
        //System.out.println(ua.getBrowser());
        return ua;
    }



}
