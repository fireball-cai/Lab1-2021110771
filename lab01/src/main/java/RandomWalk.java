import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomWalk{
    // 记录遍历路径的节点  
    public List<String> walkPath = new ArrayList<>();
    private int[][] isVisited;
    private TuPojo tuPojo1;
    private int currentNode;

    public RandomWalk(TuPojo tuPojo){
        this.tuPojo1 = tuPojo;
        isVisited = new int[tuPojo1.numVertices][tuPojo1.numVertices];
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            for (int j = 0; j < tuPojo1.numVertices; j++) {
                isVisited[i][j]=0;
            }
        }
        currentNode = getRandomStartNode();
        walkPath.add(tuPojo1.wordIndex2Map.get(currentNode));
    }
    // 随机选择一个起始节点
    private int getRandomStartNode() {
        Random rand = new Random();
        int startNode = rand.nextInt(tuPojo1.numVertices);
        // 检查起始节点是否有出边  
        while (!hasOutgoingEdges(startNode)) {
            startNode = rand.nextInt(tuPojo1.numVertices);
        }
        return startNode;
    }

    // 检查节点是否有出边  
    private boolean hasOutgoingEdges(int node) {
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            if (tuPojo1.adjMatrix[node][i] != 0) {
                return true;
            }
        }
        return false;
    }

    // 执行随机游走  
    public String performRandomWalk() {
            List<Integer> outgoingEdges = new ArrayList<>();
            for (int i = 0; i < tuPojo1.numVertices; i++) {
                if (tuPojo1.adjMatrix[currentNode][i] != 0) {
                    outgoingEdges.add(i);
                }
            }
            // 检查是否有出边
            if (outgoingEdges.isEmpty()) {
                return "当前节点不存在出边";
            }
            // 随机选择一个出边  
            Random rand = new Random();
            int nextNode = outgoingEdges.get(rand.nextInt(outgoingEdges.size()));

            // 如果重复访问边，则返回null
            if (isVisited[currentNode][nextNode] == 1){
                return "重复访问边";
            }

            // 移动到下一个节点
            isVisited[currentNode][nextNode] = 1;
            currentNode = nextNode;
            walkPath.add(tuPojo1.wordIndex2Map.get(currentNode));
            return "success";
    }

    // 将遍历路径写入文件  
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            boolean isFirst = true; // 用于判断是否是第一个元素
            for (String node : walkPath) {
                if (!isFirst) {
                    writer.write(" "); // 如果不是第一个元素，则先写入一个空格
                }
                writer.write(node);
                isFirst = false; // 标记已经写入过元素
            }
            writer.newLine(); // 在所有元素之后写入一个新行（如果需要的话）
            System.out.println("Path written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}