import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Data
public class InsertBridegWords {

    public  TuPojo tuPojo;
    public  String input;

    public  FindBridegwords findBridegwords;


    private static final Pattern NON_LETTER_PATTERN = Pattern.compile("[^A-Za-z]+");

    public InsertBridegWords(TuPojo tuPojo, String input) {
        this.tuPojo = tuPojo;
        this.input = input;
        findBridegwords = new FindBridegwords();
    }

    public String GenerateBybridgewords() {
        //处理输入的字符串
        input = NON_LETTER_PATTERN.matcher(input).replaceAll(" ");
        input=input.toString().trim().replaceAll("\\s+", " ");
        String[] s=input.split("\\s+");

        String result=new String();

        for (int i=0; i<s.length-1; i++) {
            result +=s[i]+" ";
            List<String> strings = findBridegwords.find(tuPojo, s[i], s[i + 1]);
            if (strings.size()!=0 && strings.get(0) != "False") {
                Random random = new Random();
                result +=strings.get(random.nextInt(strings.size()))+" ";
            }
        }
        result+=s[s.length-1];
        return result;
    }


}
