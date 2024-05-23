import java.util.HashMap;
import java.util.Map;

public class TuPojo {
    public Map<String, Integer> wordIndexMap; // ���ʵ�������ӳ��

    public Map<Integer,String> wordIndex2Map; //�����������ҵ���
    public int numVertices; // ͼ�нڵ������
    public int[][] adjMatrix; // �ڽӾ��󣬴洢Ȩ��

    public TuPojo() {
        wordIndexMap = new HashMap<>();
        numVertices = 0;
        wordIndex2Map = new HashMap<>();
    }

    // ��ӵ��ʵ�����ӳ���У�����������
    private void addWordToIndex(String word) {
        word = word.toLowerCase(); // ���Դ�Сд
        if (!wordIndexMap.containsKey(word)) {
            int temp = numVertices;
            wordIndexMap.put(word, numVertices++);
            wordIndex2Map.put(temp,word);
        }
    }

    public void buildGraphFromString(String text) {
        // ʹ�ÿո���зִ�
        String[] words = text.split("\\s+");
        for(int i = 0; i <words.length; i++) {
            words[i] = words[i].toLowerCase();
        }
        // �������е��ʣ����������ڹ�ϵ���������ÿ�����ʶ���������ĵ������ڣ�
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

    // ʾ������������չʾ���ʵ�������ӳ��
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
