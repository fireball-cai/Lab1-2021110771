import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MAIN {

    private TuPojo tuPojo;
    private ShortestPath shortestPath;
    private RandomWalk performRandomWalk;
    private DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();
    private MAIN() {
        String Path="data.txt";
        readMytxt readmytxt = new readMytxt(Path);
        tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(readmytxt.s);
    }

    private void showDirectedGraph() {
        //1.构建dot文件
        String dotFormat=new String();
        for (int i = 0; i <tuPojo.wordIndexMap.size(); i++) {
            for (int j = 0; j < tuPojo.wordIndexMap.size(); j++) {
                if(tuPojo.adjMatrix[i][j]>0) {
                    String node1 = String.valueOf(i);
                    String node2 = String.valueOf(j);
                    String edge= String.valueOf(tuPojo.adjMatrix[i][j]);
                    dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(i));
                    dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(j));
                    dotGraphBuilder.addEdge(node1,node2,edge,"darkgreen");
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
        gv.writeGraphToFile(graph, out);
    }

    private List<String> queryBridgeWords(String word1, String word2) {
        FindBridegwords findBridegwords = new FindBridegwords();
        //寻找桥接词
        List<String> BridgeWords = findBridegwords.find(tuPojo,word1,word2);
        return  BridgeWords;

    }

    private String generateNewText(String inputtext) {
        InsertBridegWords insertBridegWords = new InsertBridegWords(tuPojo,inputtext);
        String s = insertBridegWords.GenerateBybridgewords();
        return s;
    }

    private String calShortestPath(String word1, String word2) {
        shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, word1, word2);
        return printShortestPath;
    }

    private String randomWalk(){
        String currentNodeName = performRandomWalk.performRandomWalk();
        return currentNodeName;
    }

    //主函数入口
    public static void main(String[] args) {
        //1.初始化，读入文件并生成有向图
        MAIN  main = new MAIN();
        System.out.println("已经读入文本并生成了有向图");
        while (true) {
            System.out.println("请输入想选择的功能  1:展示有向图  2:查询桥接词  3:根据bridge word生成新文本  4:计算两个单词之间的最短路径  5:随机游走");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if (next.equals("quit")) {
                System.out.println("程序已退出");
                break;
            }
            switch (next) {
                case "1":
                    main.showDirectedGraph();
                    break;
                case "2":
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("请输入想查找的桥接词");
                    String word1 = scanner1.next();
                    String word2 = scanner1.next();
                    StringBuilder result= new StringBuilder();
                    List<String> BridgeWords= main.queryBridgeWords(word1,word2);
                    //处理输出函数
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
                    }else if (BridgeWords.size()==1 && BridgeWords.get(0)=="False"){
                        result.append("No").append(" \"")
                                .append(word1).append("\" ").append("or \"")
                                .append(word2).append("\" ").append("in the graph!");
                        System.out.println(result.toString());
                    }else if (BridgeWords.size()>1){
                        result.append("The bridge words from").append(" \"").append(word1).append("\" ").append("to \"")
                                .append(word2).append("\" ").append("are:")
                                .append(String.join(",",BridgeWords));
                        System.out.println(result.toString());
                    }
                    break;
                case "3":
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("请输入一段文本");
                    String inputtext = scanner2.nextLine();
                    System.out.println(inputtext);
                    String newtext = main.generateNewText(inputtext);
                    System.out.println(newtext);
                    break;
                case "4":
                    System.out.println("可以选择的单词列表如下:");
                    for (Map.Entry<Integer,String> entry : main.tuPojo.wordIndex2Map.entrySet()) {
                        System.out.print(entry.getValue() + ",");
                    }
                    System.out.println("\n请从上面的单词中选择一个或者两个输入（以空格隔开）:");
                    Scanner scanner3 = new Scanner(System.in);
                    while(true){
                        String input = scanner3.nextLine();
                        String[] words = input.split("\\s+");
                        if(words.length == 1 && main.tuPojo.wordIndexMap.containsKey(words[0])){
                            String printShortestPath = main.calShortestPath(words[0],null);
                            System.out.print(printShortestPath);
                            main.shortestPath.plotShortestPath(main.tuPojo, words[0],null);
                            break;
                        }
                        else if(words.length == 2 && main.tuPojo.wordIndexMap.containsKey(words[0]) && main.tuPojo.wordIndexMap.containsKey(words[1])){
                            String printShortestPath = main.calShortestPath(words[0],words[1]);
                            System.out.print(printShortestPath);
                            main.shortestPath.plotShortestPath(main.tuPojo, words[0],words[1]);
                            break;
                        }
                        else{
                            System.out.println("输入有误，请重新输入");
                        }
                    }
                    break;
                case "5":
                    main.performRandomWalk = new RandomWalk(main.tuPojo);
                    System.out.println("开始执行随机游走!");
                    Scanner scanner4 = new Scanner(System.in);
                    String currentNodeName = null;
                    while (true) {
                        System.out.print("当前随机游走路径:");
                        for (String node : main.performRandomWalk.walkPath) {
                            System.out.print(node + " ");
                        }
                        System.out.print("输入exit结束随机游走，输入其他继续:");
                        if (scanner4.hasNext()) {
                            String input = scanner4.nextLine();
                            if ("exit".equalsIgnoreCase(input)) {
                                currentNodeName = "用户输入exit结束";
                                break;
                            }
                            currentNodeName = main.randomWalk();
                            if (currentNodeName.equals("当前节点不存在出边") || currentNodeName.equals("重复访问边")) {
                                break;
                            }
                        }
                    }
                    System.out.println("随机游走结束，结束原因:" + currentNodeName);
                    main.performRandomWalk.writeToFile("random_walk_path.txt");
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
