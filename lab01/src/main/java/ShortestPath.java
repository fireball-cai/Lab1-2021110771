import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ShortestPath {
    private boolean[] visited;
    private int[] minDistance;
    private int[] parent;

    //ͨ��dijkstra�㷨����ͼ�е����·��
    public String dijkstra(TuPojo tuPojo, String word1, String word2) {
        visited = new boolean[tuPojo.numVertices];
        minDistance = new int[tuPojo.numVertices];
        parent = new int[tuPojo.numVertices];
        //��ʼ��
        Arrays.fill(visited, false);
        Arrays.fill(minDistance, -1); //-1��ʾ���������
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
        //��ʼ��ѭ����ÿ�μ����Դ�㵽ĳ����������·��
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

        //���·�������������д�ӡ������shortestPash�������·���ϵĽ������printshortestPath�������յ����·��
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
                    printshortestPath.append("����").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("������").append(tuPojo.wordIndex2Map.get(i)).append("�����·��Ϊ:");
                    for (int j = shortestPath.size() - 1; j > 0; j--) {
                        printshortestPath.append(shortestPath.get(j)).append("->");
                    }
                    printshortestPath.append(shortestPath.get(0));
                    printshortestPath.append("\n·���ĳ���Ϊ:").append(minDistance[i]).append("\n");
                    shortestPath.clear();
                } else {
                    if (i != sourcePoint) {
                        printshortestPath.append("����").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("������").append(tuPojo.wordIndex2Map.get(i)).append("�����·��Ϊ:�������ʲ��ɴ�\n");
                    }
                }
            }
            return printshortestPath.toString();
        }
        else {
            int endPoint = tuPojo.wordIndexMap.get(word2);
            if (!visited[endPoint]) {
                return "����" + tuPojo.wordIndex2Map.get(sourcePoint) + "������" + tuPojo.wordIndex2Map.get(endPoint) + "�����·��Ϊ:�������ʲ��ɴ�\n";
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
                printshortestPath.append("����").append(tuPojo.wordIndex2Map.get(sourcePoint)).append("������").append(tuPojo.wordIndex2Map.get(endPoint)).append("�����·��Ϊ:");
                for (int i = shortestPath.size() - 1; i > 0; i--) {
                    printshortestPath.append(shortestPath.get(i)).append("->");
                }
                printshortestPath.append(shortestPath.get(0));
                printshortestPath.append("\n·���ĳ���Ϊ:").append(minDistance[endPoint]).append("\n");
                return printshortestPath.toString();
            }
        }
    }

    //������ͼ�ϱ�ע���·�������ɱ�ע���ͼ
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

                    //������ͼ�ϱ�ע���·��,��ÿ����ͬ�Ŀɴ�������һ�ű�ע����ͼ
                    DotGraphBuilder dotGraphBuilder = new DotGraphBuilder();
                    //1.����dot�ļ�
                    String dotFormat = new String();
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
                                if(shortestPath.contains(tuPojo.wordIndex2Map.get(j)) && shortestPath.contains(tuPojo.wordIndex2Map.get(k)) && shortestPath.indexOf(tuPojo.wordIndex2Map.get(j))>shortestPath.indexOf(tuPojo.wordIndex2Map.get(k))){
                                    dotGraphBuilder.addEdge(node1,node2,edge,"red");
                                }
                                else{
                                    dotGraphBuilder.addEdge(node1,node2,edge,"darkgreen");
                                }
                            }
                        }
                    }
                    String s = String.valueOf(minDistance[i]);
                    s="mindistanse:"+s;
                    dotGraphBuilder.addNode(String.valueOf(tuPojo.wordIndex2Map.size()), s, "red");
                    dotFormat = dotGraphBuilder.build();

                    //2.���л�ͼ
                    Graphviz gv = new Graphviz();
                    gv.addln(gv.start_graph());
                    gv.add(dotFormat);
                    gv.addln(gv.end_graph());
                    gv.decreaseDpi();
                    gv.decreaseDpi();

                    String fileName = shortestPath.get(shortestPath.size() - 1) + "_" + shortestPath.get(0);
                    String folderName = "shortestPathImage";
                    String type = "png";// pngΪ�����ʽ�����ɸ�Ϊpdf��gif��jpg��

                    // �����ļ��У�������������ڣ�
                    File folder = new File(folderName);
                    if (!folder.exists()) {
                        folder.mkdirs(); // �����ļ��У��������б�Ҫ�ĸ��ļ���
                    }
                    else {
                        // ����ļ��д��ڣ������Ƿ�����ļ��������
                        File[] files = folder.listFiles();
                        if (files != null && (i == 0 || (i == 1 && sourcePoint == 0))) {
                            for (File file : files) {
                                if (!file.isDirectory()) { // ֻɾ���ļ�����ɾ�����ļ���
                                    if (!file.delete()) {
                                        System.err.println("�޷�ɾ���ļ���" + file.getAbsolutePath());
                                    }
                                }
                            }
                        }
                    }
                    File out = new File(folder, fileName + "." + type);
                    byte[] graph = gv.getGraph(gv.getDotSource(), type);

                    //3.����ͼ��
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

                //������ͼ�ϱ�ע���·��
                DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();
                //1.����dot�ļ�
                String dotFormat = new String();
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
                            if(shortestPath.contains(tuPojo.wordIndex2Map.get(i)) && shortestPath.contains(tuPojo.wordIndex2Map.get(j)) && shortestPath.indexOf(tuPojo.wordIndex2Map.get(i))>shortestPath.indexOf(tuPojo.wordIndex2Map.get(j))){
                                dotGraphBuilder.addEdge(node1,node2,edge,"red");
                            }
                            else{
                                dotGraphBuilder.addEdge(node1,node2,edge,"darkgreen");
                            }
                        }
                    }
                }
                String s = String.valueOf(minDistance[endPoint]);
                s="mindistanse:"+s;
                dotGraphBuilder.addNode(String.valueOf(tuPojo.wordIndex2Map.size()), s, "red");
                dotFormat=dotGraphBuilder.build();

                //2.���л�ͼ
                Graphviz gv= new Graphviz();
                gv.addln(gv.start_graph());
                gv.add(dotFormat);
                gv.addln(gv.end_graph());
                gv.decreaseDpi();
                gv.decreaseDpi();

                String fileName = "Dotgraph";
                String folderName = "shortestPathImage";
                String type = "png";// pngΪ�����ʽ�����ɸ�Ϊpdf��gif��jpg��

                // �����ļ��У�������������ڣ�
                File folder = new File(folderName);
                if (!folder.exists()) {
                    folder.mkdirs(); // �����ļ��У��������б�Ҫ�ĸ��ļ���
                }
                else {
                    // ����ļ��д��ڣ������Ƿ�����ļ��������
                    File[] files = folder.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (!file.isDirectory()) { // ֻɾ���ļ�����ɾ�����ļ���
                                if (!file.delete()) {
                                    System.err.println("�޷�ɾ���ļ���" + file.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
                File out = new File(folder, fileName+"."+ type);
                byte[] graph = gv.getGraph(gv.getDotSource(), type);

                //3.����ͼ��
                gv.writeGraphToFile(graph, out);
            }
        }
    }
}
