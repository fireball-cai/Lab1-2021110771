import java.util.HashMap;
import java.util.Map;

public class TuPojo {
    public Map<String, Integer> wordIndexMap; // 单词到索引的映射

    public Map<Integer,String> wordIndex2Map; //根据索引查找单词
    public int numVertices; // 图中节点的数量
    public int[][] adjMatrix; // 邻接矩阵，存储权重

    public TuPojo() {
        wordIndexMap = new HashMap<>();
        numVertices = 0;
        wordIndex2Map = new HashMap<>();
    }

    // 添加单词到索引映射中，并返回索引
    private void addWordToIndex(String word) {
        word = word.toLowerCase(); // 忽略大小写
        if (!wordIndexMap.containsKey(word)) {
            int temp = numVertices;
            wordIndexMap.put(word, numVertices++);
            wordIndex2Map.put(temp,word);
        }
    }

    public void buildGraphFromString(String text) {
        // 使用空格进行分词
        String[] words = text.split("\\s+");
        for(int i = 0; i <words.length; i++) {
            words[i] = words[i].toLowerCase();
        }
        // 遍历所有单词，并构建相邻关系（这里假设每个单词都与它后面的单词相邻）
        for (int i = 0; i < words.length - 1; i++) {
            String fromWord = words[i];
            String toWord = words[i + 1];
            addWordToIndex(fromWord);
            addWordToIndex(toWord);
        }

        int len=wordIndexMap.size();
        adjMatrix=new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                adjMatrix[i][j]=0;
            }
        }
        for (String word:wordIndexMap.keySet()) {
            int index = wordIndexMap.get(word);

            for (int j=index;j<words.length;j++) {
                if (j==words.length-1)
                    break;
                if (word.equals(words[j])) {
                     int index2 = wordIndexMap.get(words[j + 1]);
                     adjMatrix[index][index2] +=1;
                }
            }
        }

    }

    // 示例方法，用于展示单词到索引的映射
    public void printWordIndexMap() {
        for (Map.Entry<String, Integer> entry : wordIndexMap.entrySet()) {
            System.out.println("Word: " + entry.getKey() + ", Index: " + entry.getValue());
        }

        for (Map.Entry<Integer,String> entry : wordIndex2Map.entrySet()) {
            System.out.println("Word: " + entry.getKey() + ", Index: " + entry.getValue());
        }
    }

    public void printAdjMatrix()
    {
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                //System.out.print(adjMatrix[i][j] + " ");
                if(adjMatrix[i][j]>0){
                    System.out.print(adjMatrix[i][j] + " ");
                    System.out.print(" "+wordIndex2Map.get(i));
                    System.out.print(" "+wordIndex2Map.get(j)+" ");
                }
            }
            System.out.println();
        }
    }
}
