import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomWalk{
    // ¼ÇÂ¼±éÀúÂ·¾¶µÄ½Úµã  
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
    // Ëæ»úÑ¡ÔñÒ»¸öÆðÊ¼½Úµã
    private int getRandomStartNode() {
        Random rand = new Random();
        int startNode = rand.nextInt(tuPojo1.numVertices);
        // ¼ì²éÆðÊ¼½ÚµãÊÇ·ñÓÐ³ö±ß  
        while (!hasOutgoingEdges(startNode)) {
            startNode = rand.nextInt(tuPojo1.numVertices);
        }
        return startNode;
    }

    // ¼ì²é½ÚµãÊÇ·ñÓÐ³ö±ß  
    private boolean hasOutgoingEdges(int node) {
        for (int i = 0; i < tuPojo1.numVertices; i++) {
            if (tuPojo1.adjMatrix[node][i] != 0) {
                return true;
            }
        }
        return false;
    }

    // Ö´ÐÐËæ»úÓÎ×ß  
    public String performRandomWalk() {
            List<Integer> outgoingEdges = new ArrayList<>();
            for (int i = 0; i < tuPojo1.numVertices; i++) {
                if (tuPojo1.adjMatrix[currentNode][i] != 0) {
                    outgoingEdges.add(i);
                }
            }
            // ¼ì²éÊÇ·ñÓÐ³ö±ß
            if (outgoingEdges.isEmpty()) {
                return "µ±Ç°½Úµã²»´æÔÚ³ö±ß";
            }
            // Ëæ»úÑ¡ÔñÒ»¸ö³ö±ß  
            Random rand = new Random();
            int nextNode = outgoingEdges.get(rand.nextInt(outgoingEdges.size()));

            // Èç¹ûÖØ¸´·ÃÎÊ±ß£¬Ôò·µ»Ønull
            if (isVisited[currentNode][nextNode] == 1){
                return "ÖØ¸´·ÃÎÊ±ß";
            }

            // ÒÆ¶¯µ½ÏÂÒ»¸ö½Úµã
            isVisited[currentNode][nextNode] = 1;
            currentNode = nextNode;
            walkPath.add(tuPojo1.wordIndex2Map.get(currentNode));
            return "success";
    }

    // ½«±éÀúÂ·¾¶Ð´ÈëÎÄ¼þ  
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            boolean isFirst = true; // ÓÃÓÚÅÐ¶ÏÊÇ·ñÊÇµÚÒ»¸öÔªËØ
            for (String node : walkPath) {
                if (!isFirst) {
                    writer.write(" "); // Èç¹û²»ÊÇµÚÒ»¸öÔªËØ£¬ÔòÏÈÐ´ÈëÒ»¸ö¿Õ¸ñ
                }
                writer.write(node);
                isFirst = false; // ±ê¼ÇÒÑ¾­Ð´Èë¹ýÔªËØ
            }
            writer.newLine(); // ÔÚËùÓÐÔªËØÖ®ºóÐ´ÈëÒ»¸öÐÂÐÐ£¨Èç¹ûÐèÒªµÄ»°£©
            System.out.println("Path written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}[C[D//test content