import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShortestPath {
    public TuPojo tuPojo;
    public  DotGraphBuilder dotGraphBuilder=new DotGraphBuilder();

    @BeforeEach
    public void beforeAll() {
        String Path="data.txt";
        readMytxt readmytxt = new readMytxt(Path);
        tuPojo = new TuPojo();
        tuPojo.buildGraphFromString(readmytxt.s);
    }

    @Test
    public void testcase1(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "to", null);
        System.out.print(printShortestPath);
    }

    @Test
    public void testcase2(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "civilizations", null);
        System.out.print(printShortestPath);
    }

    @Test
    public void testcase3(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "yes", null);
        System.out.print(printShortestPath);
    }

    @Test
    public void testcase4(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "to", "and");
        System.out.print(printShortestPath);
    }

    @Test
    public void testcase5(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "civilizations", "to");
        System.out.print(printShortestPath);
    }

    @Test
    public void testcase6(){
        ShortestPath shortestPath = new ShortestPath();
        String printShortestPath = shortestPath.dijkstra(tuPojo, "yes", "to");
        System.out.print(printShortestPath);
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Test passed");
    }
}
