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
        //1.����dot�ļ�
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

        //2.���л�ͼ
        Graphviz gv= new Graphviz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // pngΪ�����ʽ�����ɸ�Ϊpdf��gif��jpg��
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        byte[] graph = gv.getGraph(gv.getDotSource(), type);

        //3.����ͼ��
        gv.writeGraphToFile(graph, out);
    }

    private List<String> queryBridgeWords(String word1, String word2) {
        FindBridegwords findBridegwords = new FindBridegwords();
        //Ѱ���ŽӴ�
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

    //���������
    public static void main(String[] args) {
        //1.��ʼ���������ļ�����������ͼ
        MAIN  main = new MAIN();
        System.out.println("�Ѿ������ı�������������ͼ");
        while (true) {
            System.out.println("��������ѡ��Ĺ���  1:չʾ����ͼ  2:��ѯ�ŽӴ�  3:����bridge word�������ı�  4:������������֮������·��  5:�������");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if (next.equals("quit")) {
                System.out.println("�������˳�");
                break;
            }
            switch (next) {
                case "1":
                    main.showDirectedGraph();
                    break;
                case "2":
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("����������ҵ��ŽӴ�");
                    String word1 = scanner1.next();
                    String word2 = scanner1.next();
                    StringBuilder result= new StringBuilder();
                    List<String> BridgeWords= main.queryBridgeWords(word1,word2);
                    //�����������
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
                    System.out.println("������һ���ı�");
                    String inputtext = scanner2.nextLine();
                    System.out.println(inputtext);
                    String newtext = main.generateNewText(inputtext);
                    System.out.println(newtext);
                    break;
                case "4":
                    System.out.println("����ѡ��ĵ����б�����:");
                    for (Map.Entry<Integer,String> entry : main.tuPojo.wordIndex2Map.entrySet()) {
                        System.out.print(entry.getValue() + ",");
                    }
                    System.out.println("\n�������ĵ�����ѡ��һ�������������루�Կո������:");
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
                            System.out.println("������������������");
                        }
                    }
                    break;
                case "5":
                    main.performRandomWalk = new RandomWalk(main.tuPojo);
                    System.out.println("��ʼִ���������!");
                    Scanner scanner4 = new Scanner(System.in);
                    String currentNodeName = null;
                    while (true) {
                        System.out.print("��ǰ�������·��:");
                        for (String node : main.performRandomWalk.walkPath) {
                            System.out.print(node + " ");
                        }
                        System.out.print("����exit����������ߣ�������������:");
                        if (scanner4.hasNext()) {
                            String input = scanner4.nextLine();
                            if ("exit".equalsIgnoreCase(input)) {
                                currentNodeName = "�û�����exit����";
                                break;
                            }
                            currentNodeName = main.randomWalk();
                            if (currentNodeName.equals("��ǰ�ڵ㲻���ڳ���") || currentNodeName.equals("�ظ����ʱ�")) {
                                break;
                            }
                        }
                    }
                    System.out.println("������߽���������ԭ��:" + currentNodeName);
                    main.performRandomWalk.writeToFile("random_walk_path.txt");
                    break;
                default:
                    System.out.println("������������������");
                    break;
            }
        }
    }
}
