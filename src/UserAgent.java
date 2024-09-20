import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    public String line;
    public String browser;
    public String os;


    public UserAgent(String line) {
        this(line, "", "");
    }

    public UserAgent(String line, String browser, String os) {
        this.line = line;
        this.browser = Browser();
        this.os = Os();
    }

    public String Os() {
        String strOs = "нет";
        if (line.matches(".*Linux.*")) {
            strOs = "Linux";
        }
        if (line.matches(".*Mac OS.*")) {
            strOs = "Mac Os";
        }
        if (line.matches(".*Windows.*")) {
            strOs = "Windows";
        }
        return strOs;
    }

    public String Browser() {
        String strBrowser = "нет никакой";
        if (line.matches(".*Edge.*")) {
            strBrowser = "Edge";
        }
        if (line.matches(".*Firefox.*")) {
            strBrowser = "Firefox";
        }
        if (line.matches(".*Chrome.*")) {
            strBrowser = "Chrome";
        }
        if (line.matches(".*Opera.*")) {
            strBrowser = "Opera";
        }
        if (line.matches(".*Safari.*")) {
            strBrowser = "Safari";
        }
        if (line.matches(".*bot.*")) {
            strBrowser = "bot";
        }
        if (line.matches(".*Bot.*")) {
            strBrowser = "bot";
        }
        if (line.matches(".*curl.*")) {
            strBrowser = "library";
        }
        if (line.matches(".*Postman.*")) {
            strBrowser = "net_tool";
        }
        return strBrowser;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "browser='" + browser + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
