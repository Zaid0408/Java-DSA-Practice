import java.util.*;
public class Graph {
    // graph can be represented with the help of adjacency list
    // list within a list is called adjacency list
    // each list is a no
    // if i and j nodes are not connected they are represented by i,j as 1 for unweighted and i,j as weight for weighted graph

    // Arraylist of type Edge
    // Edge is defined in the class as Edge =(src,dest,weight)
    
    static class edge2{ // unweighted edge
        int src,dest;
        public edge2(int src,int dest){
            this.src=src;
            this.dest=dest;
        }
    }
    static class Edge{ // weighted edge
        int src,dest,weight;
        public Edge(int src,int dest,int weight){
            this.src=src;
            this.dest=dest;
            this.weight=weight;
        }
    }
    // creating graph from scratch unweighted
    public static void createGraph(ArrayList<edge2>[] graph){
        for(int i=0;i<graph.length;i++){
            graph[i]=new ArrayList<>();
            // creating a new arraylist for each Edge
            // just creation and assigning memory it has no values;
        }
        // 0 vertex
        graph[0].add(new edge2(0,1));
        graph[0].add(new edge2(0,2));

        // 1 vertex
        graph[1].add(new edge2(1,0));
        graph[1].add(new edge2(1,3));

        // 2 vertex
        graph[2].add(new edge2(2,0));
        graph[2].add(new edge2(2,4));

        // 3 vertex
        graph[3].add(new edge2(3,1));
        graph[3].add(new edge2(3,5));

        // 4 vertex
        graph[4].add(new edge2(4,2));
        graph[4].add(new edge2(4,5));

        // 5 vertex
        graph[5].add(new edge2(5,3));
        graph[5].add(new edge2(5,4));
        graph[5].add(new edge2(5,6));

        // 6 vertex
        graph[6].add(new edge2(6,5));

        /*The foll graph is made
         * 
         *      1 --- 3
         *     /       \
         *    /         \
         *  0            5 ---- 6 
         *    \         /
         *     \       /
         *      2 --- 4
         */


    }
    public static void bfs(ArrayList<edge2>[] graph , int src){
        Queue<Integer> q=new LinkedList<>();
        boolean visited[]=new boolean[graph.length];

        q.add(src);
        while(!q.isEmpty()){
            int curr=q.remove();
            if(!visited[curr]){
                visited[curr]=true;
                System.out.print(curr+" ");
                // add all the neighbours of src in the queue
                for(int i=0;i<graph[curr].size();i++){
                    edge2 e=graph[curr].get(i); // get each edge from the arraylist of src vertex
                    q.add(e.dest);
                }
            }
        }
    }
    // dfs using recursion or stack. when we use recursion we as it is use a stack because of many function calls
    // using stack in this case
    public static void dfs(ArrayList<edge2>[] graph , int src){
        boolean visited[]=new boolean[graph.length];
        Stack<Integer> st=new Stack<>();
        st.push(src);

        while(!st.isEmpty()){
            int curr=st.pop();
            if(!visited[curr]){
                visited[curr]=true;
                System.out.print(curr+" ");
                // add all the neighbours of src in the stack
                for(int i=0;i<graph[curr].size();i++){
                    edge2 e=graph[curr].get(i); // get each edge from the arraylist of src vertex
                    st.push(e.dest);
                }
            }
        }
    }
    // finding if there is a path from src to dest in a joined graph
    public static void hasPath(ArrayList<edge2>[] graph,int src,int dest){
        // use dfs to find the path
        boolean visited[]=new boolean[graph.length];
        Stack<Integer> st=new Stack<>();
        st.push(src);

        while(!st.isEmpty()){
            int curr=st.pop();
            if(!visited[curr]){
                visited[curr]=true;
                if(curr==dest){
                    System.out.println("Path exists");
                    break;
                }
                // add all the neighbours of src in the stack
                for(int i=0;i<graph[curr].size();i++){
                    edge2 e=graph[curr].get(i); // get each edge from the arraylist of src vertex
                    st.push(e.dest);
                }
            }
        }
    }
    static boolean isCyclicUtil(int v, List<List<Integer>> adj, boolean[] visited, int parent) {
      
        // Mark the current node as visited
        visited[v] = true;

        // Recur for all the vertices
        // adjacent to this vertex
        for (int i : adj.get(v)) {
          
            // If an adjacent vertex is not visited,
            // then recur for that adjacent
            if (!visited[i]) {
                if (isCyclicUtil(i, adj, visited, v))
                    return true;
            }
          
            // If an adjacent vertex is visited and
            // is not parent of current vertex,
            // then there exists a cycle in the graph.
            else if (i != parent)
                return true;
        }
        return false;
    }

    // Returns true if the graph contains
    // a cycle, else false.
    static boolean isCyclic(int V, List<List<Integer>> adj) {
      
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];

        // Call the recursive helper function
        // to detect cycle in different DFS trees
        for (int u = 0; u < V; u++) {
          
            // Don't recur for u if it is already visited
            if (!visited[u]) {
                if (isCyclicUtil(u, adj, visited, -1))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int V=5;// weighted graph
        ArrayList<Edge>[] graph=new ArrayList[V];
        // null -> empty arraylist
        //  for each vertex or Edge there is a arraylist of type Edge
        // graph is an array of arraylist or an array of vertex where each vertex has its own arraylist

        for(int i=0;i<V;i++){
            graph[i]=new ArrayList<>();
            // creating a new arraylist for each Edge
            // just creation and assigning memory it has no values;
        }
        // 0 vertex
        graph[0].add(new Edge(0,1,5));
        // data of 0th Edge is at graph[0] position data of 1st Edge is at graph[1] position
        // for Edge 0 -> 1 we have weight 5 is the meaning of this

        // 1 vertex
        graph[1].add(new Edge(1,0,5));
        // for Edge 1 -> 2 we have weight 10 is the meaning of this
        graph[1].add(new Edge(1,2,1));
        graph[1].add(new Edge(1,3,3));

        // 2 vertex
        graph[2].add(new Edge(2,1,1));
        graph[2].add(new Edge(2,3,3));
        graph[2].add(new Edge(2,4,2));

        // 3 vertex
        graph[3].add(new Edge(3,1,3));
        graph[3].add(new Edge(3,2,1));

        // 4 vertex
        graph[4].add(new Edge(4,2,2));

        /*
         * making the given graph below
         *                (5)
         *            0 -------- 1
         *                      / \
         *                  (1)/   \ (3)
         *                    /     \
         *                   2 ----- 3 
         *                   |  (1)
         *               (2) |
         *                   |  
         *                   4
         */

        // for(int i=0;i<graph[2].size();i++){
        //     Edge e=graph[2].get(i); // get each edge from the arraylist of vertex 2
        //     System.out.println(e.src+" "+e.dest+" "+e.weight);
        //     // print the data of each edge
        // }


        ArrayList<edge2>[] graph2=new ArrayList[7];
        createGraph(graph2);
        //bfs(graph2,3); // 3rd vertex is source
        //dfs(graph2,2);
        //hasPath(graph2, 0, 5);



    }
}
