import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class myTest {
    public TuPojo tuPojo;
    public  DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();


    @BeforeEach
    public void beforeAll() {
        String Path="data.txt";
        readMytxt readmytxt = new readMytxt(Path);
        tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(readmytxt.s);
    }

    @Test
    public void test1() {
        tuPojo.printAdjMatrix();
    }

    @Test
    public void test2() {
        tuPojo.printWordIndexMap();
    }
    @Test
    public void test3() {
        FindBridegwords findBridegwords = new FindBridegwords();
        String word1 = new String("new22");
        String word2 = new String("and99");

        //寻找桥接词

        List<String> BrigeWords = findBridegwords.find(tuPojo,word1,word2);

        StringBuilder result= new StringBuilder();
        System.out.println(BrigeWords);
        //处理输出函数
        if (BrigeWords.size() == 0) {
           System.out.println("No bridge words from"+ word1+"to"+ word2);
            result.append("No bridge words from").append(" \"").append(word1).append("\" ").append("to \"")
                    .append(word2).append("\"!");
            System.out.println(result.toString());
        }else if (BrigeWords.size()>0 && BrigeWords.get(0)!="False"){
            result.append("The bridge words from").append(" \"").append(word1).append("\" ").append("to \"")
                    .append(word2).append("\" ").append("is:").append(BrigeWords.get(0));
            System.out.println(result.toString());
        }else if (BrigeWords.size()>0 && BrigeWords.get(0)=="False"){
            result.append("No").append(" \"").append(word1).append("\" ").append("or \"")
                    .append(word2).append("\" ").append("in the graph!");
            System.out.println(result.toString());
        }
    }

    @Test
    public void test4() {
        InsertBridegWords insertBridegWords = new InsertBridegWords(tuPojo,"Seek to explore new and \n" +
                "exciting synergies" );
        String s = insertBridegWords.GenerateBybridgewords();
        System.out.println(s);
    }
    
    @Test
    public void test5() {

        //1.构建dot文件

        String dotFormat = new String();

        for (int i = 0; i <tuPojo.wordIndexMap.size(); i++) {
            for (int j = 0; j < tuPojo.wordIndexMap.size(); j++) {
                if(tuPojo.adjMatrix[i][j]>0) {
                    String node1 = String.valueOf(i);
                    String node2 = String.valueOf(j);
                    String edge= String.valueOf(tuPojo.adjMatrix[i][j]);

                    dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(i),"red");
                    dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(j),"red");
                    dotGraphBuilder.addEdge(node1,node2,edge,"red");
                }
            }
        }
        dotFormat=dotGraphBuilder.build();
        String fileName = "Dotgraph";


        //2.进行绘图
        Graphviz gv= new Graphviz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // png为输出格式，还可改为pdf，gif，jpg等
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        byte[] graph = gv.getGraph(gv.getDotSource(), type);

        //3.保存图像
        gv.writeGraphToFile(graph, out );
    }

    @AfterEach
    public void afterEach() {
        System.out.println("测试通过");
    }

}
