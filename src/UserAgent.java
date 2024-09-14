import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    public String line;
    public String browser;
    public String os;


    public UserAgent(String line){
        this(line,"","");
    }
    public UserAgent(String line,String browser, String os ) {
        this.line = line;
        this.browser = Browser();
        this.os = Os();
    }
    public String Os(){
        String str;
        Pattern p = Pattern.compile("(?=((Linux)|(Mac OS)|(Windows)))"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
        Matcher m = p.matcher(this.line);
        str = m.find() ? m.group() : "Not found";
        return str;
    }
    public String Browser(){
        String str;
        Pattern p = Pattern.compile("(?=((Edge)|(Firefox)|(Chrome)|(Opera)))"); // нагуглил исходно такую регулярку: "[(\\[{](.*?)[)\\]}]"
        Matcher m = p.matcher(this.line);
        str = m.find() ? m.group() : "Not found";
        return str;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOs() {
        return os;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
