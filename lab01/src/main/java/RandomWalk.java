import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomWalk {
    // ��¼����·���Ľڵ�  
    public List<String> walkPath = new ArrayList<>();
    private int[][] isVisited;
    private TuPojo tuPojo1;
    private int currentNode;

    public RandomWalk(TuPojo tuPojo) {
        this.tuPojo1 = tuPojo;
        isVisited = new int[tuPojo1.numVertices][tuPojo1.numVertices];
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            for (int j = 0; j < tuPojo1.numVertices; j++) {
                isVisited[i][j] = 0;
            }
        }
        currentNode = getRandomStartNode();
        walkPath.add(tuPojo1.wordIndex2Map.get(currentNode));
    }

    // ���ѡ��һ����ʼ�ڵ�
    private int getRandomStartNode() {
        Random rand = new Random();
        int startNode = rand.nextInt(tuPojo1.numVertices);
        // �����ʼ�ڵ��Ƿ��г���  
        while (!hasOutgoingEdges(startNode)) {
            startNode = rand.nextInt(tuPojo1.numVertices);
        }
        return startNode;
    }

    // ���ڵ��Ƿ��г���  
    private boolean hasOutgoingEdges(int node) {
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            if (tuPojo1.adjMatrix[node][i] != 0) {
                return true;
            }
        }
        return false;
    }

    // ִ���������  
    public String performRandomWalk() {
        List<Integer> outgoingEdges = new ArrayList<>();
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            if (tuPojo1.adjMatrix[currentNode][i] != 0) {
                outgoingEdges.add(i);
            }
        }
        // ����Ƿ��г���
        if (outgoingEdges.isEmpty()) {
            return "��ǰ�ڵ㲻���ڳ���";
        }
        // ���ѡ��һ������
        Random rand = new Random();
        int nextNode = outgoingEdges.get(rand.nextInt(outgoingEdges.size()));

        // ����ظ����ʱߣ��򷵻�null
        if (isVisited[currentNode][nextNode] == 1) {
            return "�ظ����ʱ�";
        }

        // �ƶ�����һ���ڵ�
        isVisited[currentNode][nextNode] = 1;
        currentNode = nextNode;
        walkPath.add(tuPojo1.wordIndex2Map.get(currentNode));
        return "success";
    }

    // ������·��д���ļ�  
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            boolean isFirst = true; // �����ж��Ƿ��ǵ�һ��Ԫ��
            for (String node : walkPath) {
                if (!isFirst) {
                    writer.write(" "); // ������ǵ�һ��Ԫ�أ�����д��һ���ո�
                }
                writer.write(node);
                isFirst = false; // ����Ѿ�д���Ԫ��
            }
            writer.newLine(); // ������Ԫ��֮��д��һ�����У������Ҫ�Ļ���
            System.out.println("Path written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}