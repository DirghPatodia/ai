import java.util.ArrayList;
import java.util.PriorityQueue;

public class bestfS {

    static ArrayList<ArrayList<NodeEdge>> graphList = new ArrayList<>();

    static class NodeEdge implements Comparable<NodeEdge> {
        int vertex, weight;

        NodeEdge(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(NodeEdge otherEdge) {
            return otherEdge.weight - this.weight;
        }
    }

    public bestfS(int vertices) {
        for (int i = 0; i < vertices; i++) {
            graphList.add(new ArrayList<>());
        }
    }

    static void executeBestFirstSearch(int startVertex, int goalVertex, int totalVertices) {
        PriorityQueue<NodeEdge> priorityQueue = new PriorityQueue<>();
        boolean[] isVisited = new boolean[totalVertices];

        priorityQueue.add(new NodeEdge(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            int currentVertex = priorityQueue.poll().vertex;
            System.out.print(currentVertex + " ");

            if (goalVertex == currentVertex) {
                break;
            }

            for (NodeEdge adjacentVertex : graphList.get(currentVertex)) {
                if (!isVisited[adjacentVertex.vertex]) {
                    isVisited[adjacentVertex.vertex] = true;
                    priorityQueue.add(adjacentVertex);
                }
            }
        }
    }

    void addEdge(int source, int destination, int weight) {
        graphList.get(source).add(new NodeEdge(destination, weight));
        graphList.get(destination).add(new NodeEdge(source, weight));
    }

    public static void main(String[] args) {
        int verticesCount = 14;
        bestfS graph = new bestfS(verticesCount);

        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 4, 9);
        graph.addEdge(1, 5, 8);
        graph.addEdge(2, 6, 12);
        graph.addEdge(2, 7, 14);
        graph.addEdge(3, 8, 7);
        graph.addEdge(8, 9, 5);
        graph.addEdge(8, 10, 6);
        graph.addEdge(9, 11, 1);
        graph.addEdge(9, 12, 10);
        graph.addEdge(9, 13, 2);

        int startVertex = 0;
        int goalVertex = 9;

        executeBestFirstSearch(startVertex, goalVertex, verticesCount);
    }
}
