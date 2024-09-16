import java.io.*;
import java.util.ArrayList;

public class Main {
    static ArrayList<LogEntry> str = new ArrayList();
    static Statistics statistics = new Statistics();

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\pps_r\\IdeaProjects\\AccessLogParser\\access.log";
        File file = new File(path);
        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();
        if (fileExists == false) {
            System.out.println("Ошибочно введен путь, или файл не существует");

        }
        if ((fileExists == true) && (isDirectory == true)) {
            System.out.println("Это директория");

        }
        if ((fileExists == true) && (isDirectory == false)) {
            BufferedReader reader = null;
            try {
                FileReader fileReader = new FileReader(path);
                reader = new BufferedReader(fileReader);
            } catch (FileNotFoundException ffe) {
                System.out.println("FileNotFound");
            }
            String line;
            int count = 0;
            int countMozilla = 0;
            int countGooglebot = 0;
            int countYandexbot = 0;
            int length = 0;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                str.add(new LogEntry(line));
                Statistics.addEntry(str.get(i));
                System.out.println((str.get(i)).ip() +
                        "..." + ( str.get(i)).ldt() +
                        "..." + ( str.get(i)).meth() +
                        "..." + ( str.get(i)).path() +
                        "..." + ( str.get(i)).responseCode() +
                        "..." + ( str.get(i)).referer()+
                        "..." + (str.get(i)).userAgent());
                if (line.matches(".*Mozilla.*")) {
                    ++countMozilla;
                }

                if (line.matches(".*YandexBot.*")) {
                    ++countYandexbot;
                }

                if (line.matches(".*Googlebot.*")) {
                    ++countGooglebot;
                }

                if (length > 1024) {
                    throw new RuntimeException("Строка не должна быть длиннее 1024 символа");
                }

                try {
                    length = line.length();
                    ++count;
                    ++i;
                } catch (RuntimeException var1024) {
                    System.out.println("String must be shorter than 1024 characters");
                }
            }
            System.out.println("Количество строка: " + count);
            System.out.println("Число вхождений Mozilla:" + countMozilla);
            System.out.println("Число вхождений Googlebot:" + countGooglebot);
            System.out.println("Число вхождений Yandexbot:" + countYandexbot);
            System.out.println("Доля запросов от Гуглоботов:" + (double) countGooglebot / countMozilla);
            System.out.println("Доля запросов от Яндексботов:" + (double) countYandexbot / countMozilla);
            System.out.println(Statistics.getTotalTraffic());
            System.out.println(Statistics.maxTime);
        }
    }
}