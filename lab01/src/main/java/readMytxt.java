import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;

public class readMytxt {
    public  String s;

    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^A-Za-z]+");

    public readMytxt(String Path) {
        try {
            // ʹ���������������Դ�ļ�
            URL resource = readMytxt.class.getClassLoader().getResource(Path);
            if (resource != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()));
                String line;
                StringBuilder processedText = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    // �滻����ĸ�ַ�Ϊ�ո�
                    String processedLine = NON_LETTER_PATTERN.matcher(line).replaceAll(" ");
                    // ׷�ӵ�processedText��
                    processedText.append(processedLine);
                }
                // �Ƴ���ͷ�ͽ�β�Ŀո��Լ������Ŀո�
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
