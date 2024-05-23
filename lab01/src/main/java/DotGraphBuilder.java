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
        // 可以选择性地在这里添加图形的其他部分，如子图、属性等  
        return dotGraph.toString();  
    }  
  
    public static void main(String[] args) {  
        DotGraphBuilder builder = new DotGraphBuilder();  
        builder.addNode("T", "Teacher");  
        builder.addNode("P", "Pupil"); // 注意：这里可能是 "Pupil" 的拼写错误，通常可能是 "Pupil" 或 "Pupil" 的某种变体，如 "Pupil" 或 "Pupil"  
        builder.addEdge("T", "P", "Instructions", "darkgreen");  
  
        String dotFormat = builder.build();  
        System.out.println(dotFormat);  
    }  
}