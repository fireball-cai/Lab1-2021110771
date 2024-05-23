import lombok.Data;

@Data
public class DotGraphBuilder {
  
    private StringBuilder dotGraph = new StringBuilder();  
  
    public void addNode(String id, String label) {  
        dotGraph.append("    ").append(id).append(" [label=\"").append(label).append("\"]\n");  
    }
    public void addNode(String id, String label, String fontColor) {
        dotGraph.append("    ").append(id).append(" [label=\"").append(label).append("\", fontcolor=\"").append(fontColor).append("\"]\n");
    }

    public void addEdge(String from, String to, String label, String fontColor) {  
        dotGraph.append("    ").append(from).append("->").append(to).append(" [label=\"").append(label).append("\", fontcolor=\"").append(fontColor).append("\"]\n");  
    }  
  
    public String build() {  
        // ����ѡ���Ե����������ͼ�ε��������֣�����ͼ�����Ե�  
        return dotGraph.toString();  
    }  
  
    public static void main(String[] args) {  
        DotGraphBuilder builder = new DotGraphBuilder();  
        builder.addNode("T", "Teacher");  
        builder.addNode("P", "Pupil"); // ע�⣺��������� "Pupil" ��ƴд����ͨ�������� "Pupil" �� "Pupil" ��ĳ�ֱ��壬�� "Pupil" �� "Pupil"  
        builder.addEdge("T", "P", "Instructions", "darkgreen");  
  
        String dotFormat = builder.build();  
        System.out.println(dotFormat);  
    }  
}