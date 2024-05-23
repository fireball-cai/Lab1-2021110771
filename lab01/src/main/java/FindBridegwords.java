import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Data
public class FindBridegwords {
    public List<String> find(TuPojo tuPojo,String word1,String word2){

        word1=word1.toLowerCase();
        word2=word2.toLowerCase();
        //��ʼ���ŽӴ�����
        List<String> list = new ArrayList<>();

        if(!tuPojo.wordIndexMap.containsKey(word1) || !tuPojo.wordIndexMap.containsKey(word2)){
            list.add("False");
        } else{
            Integer index1 = tuPojo.wordIndexMap.get(word1);
            Integer index2 = tuPojo.wordIndexMap.get(word2);
            for (int i=0;i<tuPojo.adjMatrix[index1].length;i++){
                //�ж��Ƿ���һ����word1ָ��wordIndex2Map[i]�ı�
                if(tuPojo.adjMatrix[index1][i] >0){
                    //�ж��Ƿ���һ����wordIndex2Map[i]ָ��word2�ı�
                    if(tuPojo.adjMatrix[i][index2] >0){
                        list.add(tuPojo.wordIndex2Map.get(i));
                    }
                }
            }
        }
        return list;
    }
}
