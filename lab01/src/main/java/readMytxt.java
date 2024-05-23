import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;

public class readMytxt {
    public  String s;

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^A-Za-z]+");

    public readMytxt(String Path) {
        try {
            // 使用类加载器加载资源文件
            URL resource = readMytxt.class.getClassLoader().getResource(Path);
            if (resource != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
                String line;
                StringBuilder processedText = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // 替换非字母字符为空格
                    String processedLine = NON_LETTER_PATTERN.matcher(line).replaceAll(" ");
                    // 追加到processedText中
                    processedText.append(processedLine);
                }
                // 移除开头和结尾的空格，以及连续的空格
                s = processedText.toString().trim().replaceAll("\\s+", " ");
                reader.close();
            } else {
                System.out.println("File not found in classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
