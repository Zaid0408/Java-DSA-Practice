/* Theory 

1. Graph Definition

A graph is a collection of:

Vertices (Nodes) → Points
Edges → Connections between nodes
0 ----- 1
|      /
|     /
2 ----3
2. Types of Graphs

A. Undirected Graph

Edges work in both directions.
0 ----- 1
means
0 -> 1
1 -> 0

B. Directed Graph

Edges have a direction.

0 -----> 1
means

0 -> 1
but NOT
1 -> 0

C. Weighted Graph

Each edge has a cost.
0 ----5----> 1
Weight = 5

Most Used Representation in Leetcode Almost every graph problem uses one of these.

Representation	Usage
Adjacency List	⭐⭐⭐⭐⭐ (Most Common)
Adjacency Matrix	⭐
Edge List	⭐⭐

Focus almost entirely on Adjacency List.

3. Adjacency List (Undirected)

Example

0 --- 1
|     |
|     |
2 --- 3

Edges

0-1
0-2
1-3
2-3

Representation

0 -> 1,2
1 -> 0,3
2 -> 0,3
3 -> 1,2

Java

int V = 4;

ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());

// Undirected edge
graph.get(0).add(1);
graph.get(1).add(0);

graph.get(0).add(2);
graph.get(2).add(0);

graph.get(1).add(3);
graph.get(3).add(1);

graph.get(2).add(3);
graph.get(3).add(2);

Shortcut Function (Recommended) for undirected
static void addEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
    graph.get(u).add(v);
    graph.get(v).add(u);
}

Usage

addEdge(graph, 0, 1);
addEdge(graph, 0, 2);
addEdge(graph, 1, 3);
addEdge(graph, 2, 3);
Traversing Neighbors
for (int neighbor : graph.get(node)) {
    System.out.println(neighbor);
}

Used in

DFS
BFS
Cycle Detection
Connected Components

Basically every graph problem.

4. Directed Graph

Example

0 → 1
↓
2 → 3

Representation

0 -> 1,2
1 ->
2 -> 3
3 ->

Java

int V = 4;

ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());

graph.get(0).add(1);
graph.get(0).add(2);
graph.get(2).add(3);

Notice

We only insert

u -> v

Not

v -> u
Helper Function
static void addEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
    graph.get(u).add(v);
}
5. Weighted Graph

Most common representation:

Node -> (Neighbor, Weight)

Example

      5
0 -------- 1
 \         |
2 \        | 3
   \       |
    2 -----

Edges

0-1 weight 5
0-2 weight 2
1-2 weight 3
Pair Class (Most Common)
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}
Graph Representation
ArrayList<ArrayList<Pair>> graph = new ArrayList<>();

for (int i = 0; i < V; i++)
    graph.add(new ArrayList<>());
Adding Undirected Weighted Edge
graph.get(0).add(new Pair(1, 5));
graph.get(1).add(new Pair(0, 5));

graph.get(0).add(new Pair(2, 2));
graph.get(2).add(new Pair(0, 2));

graph.get(1).add(new Pair(2, 3));
graph.get(2).add(new Pair(1, 3));
Traversing
for (Pair nbr : graph.get(node)) {

    int next = nbr.node;
    int wt = nbr.weight;

}
Helper Function
static void addEdge(ArrayList<ArrayList<Pair>> graph,int u, int v, int wt) { // for undirected weighted graph

    graph.get(u).add(new Pair(v, wt));
    graph.get(v).add(new Pair(u, wt));
}
Directed Weighted Graph

Only one insertion.

graph.get(u).add(new Pair(v, weight));


6. Edge List

Sometimes LeetCode directly gives

int[][] edges = {
    {0,1},
    {0,2},
    {1,3},
    {2,3}
};

or

int[][] edges = {
    {0,1,5},
    {0,2,2},
    {1,2,3}
};

This is called an Edge List.

Usually, your first step is to convert it into an adjacency list.

7. Adjacency Matrix (Rare)
0 1 1 0
1 0 0 1
1 0 0 1
0 1 1 0

Java

int[][] graph = new int[V][V];

graph[0][1] = 1;
graph[1][0] = 1;

Rarely used in interviews because it uses O(V²) space.

LeetCode Graph Template (99% of Problems)
Unweighted
ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

for (int i = 0; i < n; i++)
    graph.add(new ArrayList<>());

for (int[] edge : edges) {
    int u = edge[0];
    int v = edge[1];

    graph.get(u).add(v);
    graph.get(v).add(u);   // Remove for directed graph
}
Weighted
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

ArrayList<ArrayList<Pair>> graph = new ArrayList<>();

for (int i = 0; i < n; i++)
    graph.add(new ArrayList<>());

for (int[] edge : edges) {
    int u = edge[0];
    int v = edge[1];
    int wt = edge[2];

    graph.get(u).add(new Pair(v, wt));
    graph.get(v).add(new Pair(u, wt)); // Remove for directed graph
}
Revision Notes
Undirected Graph: Store both u → v and v → u.
Directed Graph: Store only u → v.
Weighted Graph: Store (neighbor, weight) pairs instead of just the neighbor.
Adjacency List is the standard representation for almost every LeetCode graph problem.
Build the graph first, then solve it using DFS, BFS, Topological Sort, Union-Find, Dijkstra, or MST depending on the problem.

This foundation is enough for the vast majority of graph questions you'll encounter in interviews.

If input is:
int[][] edges

✅ Convert to an adjacency list.

Examples:

LC 1971
LC 785
LC 207
LC 210

If input is:
int[][] isConnected

where the matrix directly represents connections,

✅ Use the matrix directly.

Example:

LC 547
*/

import java.util.*;

public class Graph {

    /*
        Problem Statement: Given an undirected Graph consisting of V vertices numbered from 0 to V-1 and E edges. The ith edge is represented by [ai,bi], denoting a edge between vertex ai and bi. We say two vertices u and v belong to a same component if there is a path from u to v or v to u. Find the number of connected components in the graph.
A connected component is a subgraph of a graph in which there exists a path between any two vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
Same to same as lc 547 number of connected components


Examples
Input: V=4, edges=[[0,1],[1,2]]
Output: 2

0--1--2 3 
Explanation: Vertices {0,1,2} forms the first component and vertex 3 forms the second component.
Input:V = 7, edges = [[0, 1], [1, 2], [2, 3], [4, 5]]
Output: 3

0--1--2--3 4--5 here 0--1 1--2 2--3 components are connected 
Explanation: The edges [0, 1], [1, 2], [2, 3] form a connected component with vertices {0, 1, 2, 3}
The edge [4, 5] forms another connected component with vertices {4, 5}.
Therefore, the graph has 3 connected components: {0, 1, 2, 3}, {4, 5}, and the isolated vertices {6}.
    */
    public int countComponents(int V, int[][] edges) {
        // edges here are zero based indexing
        // Create adjacency list from edge list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        // Array to keep track of visited nodes
        boolean[] visited = new boolean[V];

        // Variable to count the number of connected components
        int components = 0;

        // Traverse all nodes in the graph
        for (int i = 0; i < V; i++) {

            // If the node is not visited, it's a new component
            if (!visited[i]) {
                components++;

                // Start BFS from this node
                Queue<Integer> q = new LinkedList<>();
                q.offer(i);
                visited[i] = true;

                // Perform BFS traversal
                while (!q.isEmpty()) {
                    int node = q.poll();

                    // Visit all unvisited neighbors
                    for (int nbr : adj.get(node)) {
                        if (!visited[nbr]) {
                            visited[nbr] = true;
                            q.offer(nbr);
                        }
                    }
                }
            }
        }

        // Return the total number of connected components
        return components;
    }
    // BFS on Graph (Adjacency List), bfs means immidiate neighbours and not different levels 
    // visited array to keep track of the visited nodes, starting node always placed first then traversal starts and make visited[node]=true
    // use queue, same traversal as tree

    public List<Integer> bfs(int V, List<List<Integer>> adj, int node) {// adj is adjacency list>)
        List<Integer> BFS=new ArrayList<>();
        boolean visited[]=new boolean[V];
        Queue<Integer> queue=new LinkedList<>();
        queue.offer(node); // here since we are passing node we have to add it to the quque first
        // if no specific node we can add 0 or 1 depending which index it is based on.
        visited[node]=true;
        while(!queue.isEmpty())
        {
            int front=queue.poll();
            BFS.add(front);
            for(int nbr:adj.get(front)) // get all the neighbours of node 
            {
                if(!visited[nbr])
                {
                    queue.offer(nbr);
                    visited[nbr]=true;
                }
            }
        }
        return BFS;
    } 

    // DFS on graph , depth from the node
    // Use Recursion very important 
    // For every list of a particular node we visit that node and mark it visited 
    // ex : 1 -> {2,3}
    //      2-> {4,5}
    //      3-> {6,7}
    /*
        go to 1 check 1 has 2 and 3 . select 2, 2 has 4 and 5 select 4 ,no 4 so come back
        select 5 (from 2), add 5 , nothing from 5 comeback to 2 so come back to 1 and add 3 
        from 3 go to 6 and 7. 
    */

    private void dfsRecursionHelper(int node, List<List<Integer>> adj, List<Integer> res, boolean[] visited) {
        visited[node]=true;
        res.add(node);
        for(int nbr:adj.get(node))
        {
            if(!visited[nbr])
            {
                dfsRecursionHelper(nbr,adj,res,visited);
            }
        }
    }
    public List<Integer> dfsRecursion(int V, List<List<Integer>> adj, int node) {
        boolean visited[]=new boolean[V];
        List<Integer> res=new ArrayList<>();
        dfsRecursionHelper(node,adj,res,visited);
        return res;
    }

    public List<Integer> dfs(int V, List<List<Integer>> adj, int node) {
        List<Integer> DFS=new ArrayList<>();
        boolean visited[]=new boolean[V];
        Stack<Integer> stack=new Stack<>();
        stack.push(node); // here since we are passing node we have to add it to the quque first
        // if no specific node we can add 0 or 1 depending which index it is based on.
        visited[node]=true;
        while(!stack.isEmpty())
        {
            int top=stack.pop();
            DFS.add(top);
            for(int nbr:adj.get(top)) // get all the neighbours of node 
            { // the dfs order produceed here and in recursion will be different but unless the order is important we can use any
                if(!visited[nbr])
                {
                    stack.push(nbr);
                    visited[nbr]=true;
                }
            }
        }
        return DFS;
    }



    // for lc 547 number of provinces they give adjacency matrix instead of adjacency list
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] visited = new int[n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) { // dfs on unvisited
                dfs(i, isConnected, visited);
                cnt++;
            }
        }
        return cnt;
    }
    private void dfs(int node, int[][] isConnected, int[] visited) {
        visited[node] = 1;
        for (int neighbor = 0; neighbor < isConnected.length; neighbor++) {
            if (isConnected[node][neighbor] == 1 && visited[neighbor] == 0) {
                dfs(neighbor, isConnected, visited);
            }
        }
    }
}
