import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ShortestPath {
    private boolean[] visited;
    private int[] minDistance;
    private int[] parent;

    //通过dijkstra算法计算图中的最短路径
    public String dijkstra(TuPojo tuPojo, String word1, String word2) {
        visited = new boolean[tuPojo.numVertices];
        minDistance = new int[tuPojo.numVertices];
        parent = new int[tuPojo.numVertices];
        //初始化
        Arrays.fill(visited, false);
        Arrays.fill(minDistance, -1); //-1表示距离无穷大
        Arrays.fill(parent, -1);

        int sourcePoint = tuPojo.wordIndexMap.get(word1);
        visited[sourcePoint] = true;
        minDistance[sourcePoint] = 0;
        parent[sourcePoint] = 0;
        for (int i = 1; i < tuPojo.numVertices; i++) {
            if (tuPojo.adjMatrix[sourcePoint][i] != 0) {
                minDistance[i] = tuPojo.adjMatrix[sourcePoint][i];
                parent[i] = sourcePoint;
            }
        }
        //开始主循环，每次计算出源点到某个顶点的最短路径
        for (int i = 1; i < tuPojo.numVertices; i++) {
            int minP = -1;
            int minD = Integer.MAX_VALUE;
            for (int j = 0; j < tuPojo.numVertices; j++) {
                if (!visited[j] && minDistance[j] != -1 && minDistance[j] < minD) {
                    minD = minDistance[j];
                    minP = j;
                }
            }
            if (minP == -1) {
                break;
            }
            visited[minP] = true;
            for (int j = 0; j < tuPojo.numVertices; j++) {
                if (!visited[j] && tuPojo.adjMatrix[minP][j] != 0 && (minD + tuPojo.adjMatrix[minP][j] < minDistance[j] || minDistance[j] == -1)) {
                    minDistance[j] = minD + tuPojo.adjMatrix[minP][j];
                    parent[j] = minP;
                }
            }
        }

        //最短路径计算出来后进行打印工作，shortestPash保存最短路径上的结点名，printshortestPath保存最终的最短路径
        if (word2 == null) {
            ArrayList<String> shortestPath = new ArrayList<>();
            StringBuilder printshortestPath = new StringBuilder();
            for (int i = 0; i < tuPojo.numVertices; i++) {
                if (visited[i] && i != sourcePoint) {
                    int tempVertex = i;
                    shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                    while (parent[tempVertex] != sourcePoint) {
                        tempVertex = parent[tempVertex];
                        shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                    }
                    shortestPath.add(tuPojo.wordIndex2Map.get(sourcePoint));
                    printshortestPath.append("单词").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("到单词").append(tuPojo.wordIndex2Map.get(i)).append("的最短路径为:");
                    for (int j = shortestPath.size() - 1; j > 0; j--) {
                        printshortestPath.append(shortestPath.get(j)).append("->");
                    }
                    printshortestPath.append(shortestPath.get(0));
                    printshortestPath.append("\n路径的长度为:").append(minDistance[i]).append("\n");
                    shortestPath.clear();
                } else {
                    if (i != sourcePoint) {
                        printshortestPath.append("单词").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("到单词").append(tuPojo.wordIndex2Map.get(i)).append("的最短路径为:两个单词不可达\n");
                    }
                }
            }
            return printshortestPath.toString();
        }
        else {
            int endPoint = tuPojo.wordIndexMap.get(word2);
            if (!visited[endPoint]) {
                return "单词" + tuPojo.wordIndex2Map.get(sourcePoint) + "到单词" + tuPojo.wordIndex2Map.get(endPoint) + "的最短路径为:两个单词不可达";
            } else {
                ArrayList<String> shortestPath = new ArrayList<>();
                int tempVertex = endPoint;
                shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                while (parent[tempVertex] != sourcePoint) {
                    tempVertex = parent[tempVertex];
                    shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                }
                shortestPath.add(tuPojo.wordIndex2Map.get(sourcePoint));
                StringBuilder printshortestPath = new StringBuilder();
                printshortestPath.append("单词").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("到单词").append(tuPojo.wordIndex2Map.get(endPoint)).append("的最短路径为:");
                for (int i = shortestPath.size() - 1; i > 0; i--) {
                    printshortestPath.append(shortestPath.get(i)).append("->");
                }
                printshortestPath.append(shortestPath.get(0));
                printshortestPath.append("\n路径的长度为:").append(minDistance[endPoint]).append("\n");
                return printshortestPath.toString();
            }
        }
    }

    //在有向图上标注最短路径，生成标注后的图
    public void plotShortestPath(TuPojo tuPojo, String word1, String word2) {
        if(word2 == null) {
            int sourcePoint = tuPojo.wordIndexMap.get(word1);
            ArrayList<String> shortestPath = new ArrayList<>();
            for (int i = 0; i < tuPojo.numVertices; i++) {
                if (visited[i] && i != sourcePoint) {
                    int tempVertex = i;
                    shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                    while (parent[tempVertex] != sourcePoint) {
                        tempVertex = parent[tempVertex];
                        shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                    }
                    shortestPath.add(tuPojo.wordIndex2Map.get(sourcePoint));

                    //在有向图上标注最短路径,到每个不同的可达结点生成一张标注有向图
                    DotGraphBuilder dotGraphBuilder = new DotGraphBuilder();
                    //1.构建dot文件
                    String dotFormat = new String();
                    int[][] tempAdjMatrix = new int[tuPojo.numVertices][tuPojo.numVertices];
                    for (int j = 0; j < tuPojo.numVertices; j++) {
                        for (int k = 0; k < tuPojo.numVertices; k++) {
                            tempAdjMatrix[j][k] = tuPojo.adjMatrix[j][k];
                        }
                    }
                    for (int j = shortestPath.size() - 1; j > 0; j--) {
                        int temp1 = tuPojo.wordIndexMap.get(shortestPath.get(j));
                        int temp2 = tuPojo.wordIndexMap.get(shortestPath.get(j - 1));
                        String node1 = String.valueOf(temp1);
                        String node2 = String.valueOf(temp2);
                        String edge = String.valueOf(tuPojo.adjMatrix[temp1][temp2]);
                        dotGraphBuilder.addNode(node1, tuPojo.wordIndex2Map.get(temp1), "red");
                        dotGraphBuilder.addNode(node2, tuPojo.wordIndex2Map.get(temp2), "red");
                        dotGraphBuilder.addEdge(node1, node2, edge, "red");
                        tuPojo.adjMatrix[temp1][temp2] = -1;
                    }
                    for (int j = 0; j < tuPojo.wordIndexMap.size(); j++) {
                        for (int k = 0; k < tuPojo.wordIndexMap.size(); k++) {
                            if (tuPojo.adjMatrix[j][k] > 0) {
                                String node1 = String.valueOf(j);
                                String node2 = String.valueOf(k);
                                String edge = String.valueOf(tuPojo.adjMatrix[j][k]);
                                if (shortestPath.contains(tuPojo.wordIndex2Map.get(j))) {
                                    dotGraphBuilder.addNode(node1, tuPojo.wordIndex2Map.get(j), "red");
                                } else {
                                    dotGraphBuilder.addNode(node1, tuPojo.wordIndex2Map.get(j), "darkgreen");
                                }
                                if (shortestPath.contains(tuPojo.wordIndex2Map.get(k))) {
                                    dotGraphBuilder.addNode(node2, tuPojo.wordIndex2Map.get(k), "red");
                                } else {
                                    dotGraphBuilder.addNode(node2, tuPojo.wordIndex2Map.get(k), "darkgreen");
                                }
                                dotGraphBuilder.addEdge(node1, node2, edge, "darkgreen");
                            }
                        }
                    }
                    dotFormat = dotGraphBuilder.build();
                    for (int j = 0; j < tuPojo.numVertices; j++) {
                        for (int k = 0; k < tuPojo.numVertices; k++) {
                            tuPojo.adjMatrix[j][k] = tempAdjMatrix[j][k];
                        }
                    }
                    //2.进行绘图
                    Graphviz gv = new Graphviz();
                    gv.addln(gv.start_graph());
                    gv.add(dotFormat);
                    gv.addln(gv.end_graph());
                    gv.decreaseDpi();
                    gv.decreaseDpi();

                    String fileName = shortestPath.get(shortestPath.size() - 1) + "_" + shortestPath.get(0);
                    String folderName = "shortestPathImage";
                    String type = "png";// png为输出格式，还可改为pdf，gif，jpg等

                    // 创建文件夹（如果它还不存在）
                    File folder = new File(folderName);
                    if (!folder.exists()) {
                        folder.mkdirs(); // 创建文件夹，包括所有必要的父文件夹
                    }
                    else {
                        // 如果文件夹存在，则检查是否包含文件并清空它
                        File[] files = folder.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                if (!file.isDirectory()) { // 只删除文件，不删除子文件夹
                                    if (!file.delete()) {
                                        System.err.println("无法删除文件：" + file.getAbsolutePath());
                                    }
                                }
                            }
                        }
                    }
                    File out = new File(folder, fileName + "." + type);
                    byte[] graph = gv.getGraph(gv.getDotSource(), type);

                    //3.保存图像
                    gv.writeGraphToFile(graph, out);
                    shortestPath.clear();

                }
            }
        }
        else {
            int sourcePoint = tuPojo.wordIndexMap.get(word1);
            int endPoint = tuPojo.wordIndexMap.get(word2);
            if(visited[endPoint]){
                ArrayList<String> shortestPath = new ArrayList<>();
                int tempVertex = endPoint;
                shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                while(parent[tempVertex] != sourcePoint) {
                    tempVertex = parent[tempVertex];
                    shortestPath.add(tuPojo.wordIndex2Map.get(tempVertex));
                }
                shortestPath.add(tuPojo.wordIndex2Map.get(sourcePoint));

                //在有向图上标注最短路径
                DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();
                //1.构建dot文件
                String dotFormat = new String();
                for(int i = shortestPath.size() - 1; i > 0; i--){
                    int temp1 = tuPojo.wordIndexMap.get(shortestPath.get(i));
                    int temp2 = tuPojo.wordIndexMap.get(shortestPath.get(i-1));
                    String node1 = String.valueOf(temp1);
                    String node2 = String.valueOf(temp2);
                    String edge = String.valueOf(tuPojo.adjMatrix[temp1][temp2]);
                    dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(temp1),"red");
                    dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(temp2),"red");
                    dotGraphBuilder.addEdge(node1,node2,edge,"red");
                    tuPojo.adjMatrix[temp1][temp2] = -1;
                }
                for (int i = 0; i <tuPojo.wordIndexMap.size(); i++) {
                    for (int j = 0; j < tuPojo.wordIndexMap.size(); j++) {
                        if(tuPojo.adjMatrix[i][j]>0) {
                            String node1 = String.valueOf(i);
                            String node2 = String.valueOf(j);
                            String edge = String.valueOf(tuPojo.adjMatrix[i][j]);
                            if(shortestPath.contains(tuPojo.wordIndex2Map.get(i))){
                                dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(i),"red");
                            }
                            else{
                                dotGraphBuilder.addNode(node1,tuPojo.wordIndex2Map.get(i),"darkgreen");
                            }
                            if(shortestPath.contains(tuPojo.wordIndex2Map.get(j))){
                                dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(j),"red");
                            }
                            else{
                                dotGraphBuilder.addNode(node2,tuPojo.wordIndex2Map.get(j),"darkgreen");
                            }
                            dotGraphBuilder.addEdge(node1,node2,edge,"darkgreen");
                        }
                    }
                }
                dotFormat=dotGraphBuilder.build();

                //2.进行绘图
                Graphviz gv= new Graphviz();
                gv.addln(gv.start_graph());
                gv.add(dotFormat);
                gv.addln(gv.end_graph());
                gv.decreaseDpi();
                gv.decreaseDpi();

                String fileName = "Dotgraph";
                String folderName = "shortestPathImage";
                String type = "png";// png为输出格式，还可改为pdf，gif，jpg等

                // 创建文件夹（如果它还不存在）
                File folder = new File(folderName);
                if (!folder.exists()) {
                    folder.mkdirs(); // 创建文件夹，包括所有必要的父文件夹
                }
                else {
                    // 如果文件夹存在，则检查是否包含文件并清空它
                    File[] files = folder.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (!file.isDirectory()) { // 只删除文件，不删除子文件夹
                                if (!file.delete()) {
                                    System.err.println("无法删除文件：" + file.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
                File out = new File(folder, fileName+"."+ type);
                byte[] graph = gv.getGraph(gv.getDotSource(), type);

                //3.保存图像
                gv.writeGraphToFile(graph, out);
            }
        }
    }
}
