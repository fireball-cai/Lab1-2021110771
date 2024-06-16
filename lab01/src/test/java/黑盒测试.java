import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ºÚºĞ²âÊÔ {
    public List<String> BridgeWords;
    public String word1 = new String();
    public  String word2 = new String("once");
    public Main main=new Main();

    @Test
    public void test1() {
        word1="once";
        word2="a";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test2() {
//        TuPojo tuPojo = new TuPojo();
//        tuPojo.buildGraphFromString(new String());
        word1="once";
        word2="the";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test3() {
        word1="a";
        word2="in";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test4() {
       TuPojo tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(new String());
        word1="once";
        word2="a";
        BridgeWords = main.queryBridgeWords(tuPojo,word1,word2);
    }

    @Test
    public void test5() {
        word1="";
        word2="the";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test6() {
        word1="once";
        word2="";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }


    @Test
    public void test7() {
        word1="";
        word2="";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test8() {
        word1="@the";
        word2="";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test9() {
        word1="";
        word2="@the";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test10() {
        word1="a";
        word2="@the";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }

    @Test
    public void test11() {
        word1="@the";
        word2="once";
        BridgeWords = main.queryBridgeWords(main.getTuPojo(),word1,word2);
    }










    @AfterEach
    public void afterEach() {
        StringBuilder result= new StringBuilder();
        if (BridgeWords.size() == 0) {
            result.append("No bridge words from").append(" \"")
                    .append(word1).append("\" ").append("to \"")
                    .append(word2).append("\"!");
            System.out.println(result.toString());
        }else if (BridgeWords.size()==1 && BridgeWords.get(0)!="False"){
            result.append("The bridge words from").append(" \"")
                    .append(word1).append("\" ").append("to \"")
                    .append(word2).append("\" ").append("is:").append(BridgeWords.get(0));
            System.out.println(result.toString());
        }else if (BridgeWords.size()==2 && BridgeWords.get(0)=="False"){
            if (BridgeWords.get(1)=="False1"){
                result.append("No").append(" \"")
                        .append(word1).append("\" ").append("in the graph!");
            }else if(BridgeWords.get(1)=="False2"){
                result.append("No").append(" \"")
                        .append(word2).append("\" ").append("in the graph!");
            } else if (BridgeWords.get(1)=="False12") {
                result.append("No").append(" \"")
                        .append(word1).append("\" ").append("and").append(" \"")
                        .append(word2).append("\" ").append("in the graph!");
            }
            System.out.println(result.toString());
        }else if (BridgeWords.size()>1){
            result.append("The bridge words from").append(" \"").append(word1).append("\" ").append("to \"")
                    .append(word2).append("\" ").append("are:")
                    .append(String.join(",",BridgeWords));
            System.out.println(result.toString());
        }
    }
}
