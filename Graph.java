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

    /*  Count Components
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
        for (int i = 0; i < V; i++) { // this loop is needed as not all vertices and edges are connected

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
    // leetcode 998 Rotten Oranges
    // rotten problem, check commens for understanding
    class Pair{
        int a;int b;

        Pair(int a, int b)
        {
            this.a=a;
            this.b=b;
        }
    }
    public int orangesRotting(int[][] grid) {
        int minutes=0;int fresh=0;
        int n=grid.length;int m=grid[0].length;
        Queue<Pair> q=new LinkedList<Pair>();
        // Find all the rotten oranges and push in queue as bfs should start from there
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==2)
                {
                    q.offer(new Pair(i,j));
                }
                else if(grid[i][j]==1)
                {
                    fresh++;
                }
            }
        }
        if(fresh==0) return 0; // no fresh oranges no answer
        // BFS
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1}; // use a pair of drow[i] or dcol[i] to go in a certain direction
        while (!q.isEmpty()) {
            int size = q.size(); // Number of oranges rotting at this current minute
            // Instead of maintaining a separate boolean[][] rotten array, you can modify the grid directly
            boolean rottedThisMinute = false; // instead of maintaining a list check if any rotting occurred at this minute in this loop
            for (int k = 0; k < size; k++) {
                Pair p=q.poll();
                int r=p.a;
                int c=p.b;

                // Check all 4 adjacent sides
                for (int d = 0; d < 4; d++) {
                    int nRow = r + dRow[d];
                    int nCol = c + dCol[d];

                    // Boundary check and verifying if it's a fresh orange
                    if (nRow >= 0 && nRow < n && nCol >= 0 && nCol < m && grid[nRow][nCol] == 1) {
                        grid[nRow][nCol] = 2; // Mark it as rotten directly in the grid
                        fresh--;       // One less fresh orange left
                        q.offer(new Pair(nRow, nCol));
                        rottedThisMinute = true;
                    }
                }
            }
            
            // Only increment time if this layer actually spread the rot further
            if (rottedThisMinute) {
                minutes++;
            }
        }
        return fresh==0?minutes:-1;
    }
    /*
    Why Rotten Oranges needs size but Flood Fill doesn't
Rotten Oranges
Minute 0
R F F

↓

Minute 1
R R F

↓

Minute 2
R R R
    */

    // leetcode 733 Flood fill: Similar to rotten oranges
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        Queue<Pair> q=new LinkedList<>();
        int n=image.length;int m=image[0].length;
        int orignal=image[sr][sc];
        if(orignal==color)
            return image;
        image[sr][sc]=color;
        q.offer(new Pair(sr,sc));
        while(!q.isEmpty())
        {
            int size=q.size();
            Pair p=q.poll();
            int r=p.a;int c=p.b;
            for(int d=0;d<4;d++)
            {
                int row=r+dRow[d];
                int col=c+dCol[d];
                if(row<n && row>=0 && col<m && col>=0 && image[row][col]==orignal)
                {
                    image[row][col]=color;
                    q.offer(new Pair(row,col));
                }
            }
        }

        return image;

    }
    // Detect cycle in undirected graph using BFS
    // Store (node, parent) in the BFS queue.
// If a neighbor is unvisited, visit it and remember the current node as its parent.
// If a neighbor is already visited and is NOT the parent,
// then we have reached the same node through another path,
// which means the undirected graph contains a cycle.
// Run BFS/DFS from every unvisited node to handle disconnected graphs.
    public boolean isCycle(int V, List<Integer>[] adj) {
        boolean vis[]=new boolean[V];
        // impo to use this loop to check all nodes in the graph similar to countComponents implementation
        for(int i=0;i<V;i++) 
        { // this loop is impo as we have to traverse all egdes 
          // isCycleHelper will only traverse nodes starting from a particular src and that not necessary will have cyclic nature hence all nodes to be checked
            if(!vis[i])
            {
                if(isCycleHelper(i,adj,vis))
                    return true;
            }
        }
        return false;
    }
    // always remember :In an undirected graph, a cycle exists if while traversing the graph, we encounter a node that has already been visited and it is not the parent of the current node.
    private boolean isCycleHelper(int src, List<Integer>[] adj, boolean vis[]) {
        Queue<Pair> q=new LinkedList<>();
        vis[src]=true;
        q.offer(new Pair(src,-1));
        while(!q.isEmpty())
        {
            Pair p=q.poll();
            int i=p.a;int j=p.b;
            // here p.a means source/node currently and p.b means the parent of the current node. 
            // parent node is needed because if parent is already visited we can mark it as cyclic

            for(int nbr:adj[i])
            {
                if(vis[nbr]==false)
                {
                    vis[nbr]=true;
                    q.add(new Pair(nbr,i));
                }
                else if( j!=nbr)
                    return true;
            }
        }

        return false;
    }
    // dfs implementation for the same 
    private boolean isCycleHelper(int src,int parent, List<Integer>[] adj, boolean vis[]) {
        vis[src]=true;
        for(int nbr:adj[src])
        {
            if(!vis[nbr]){
                if (isCycleHelper(nbr,src,adj,vis))
                    return true;
            }
            else if(nbr!=parent)
                return true;
        }
        return false;
    }

    // detect cycle in directed graph
    /*
    Intuition:
    Make sure to carry two visited arrays in the DFS call. One is a visited array(vis) and the other is a path-visited(pathVis) array. 
    The visited array keeps a track of visited nodes, and the path-visited keeps a track of visited nodes in the current traversal only. 
While making a DFS call, at first we will mark the node as visited in both the arrays and then will traverse through its adjacent nodes. Now, there may be either of the three cases:
Case 1: If the adjacent node is not visited, we will make a new DFS call recursively with that particular node.
Case 2: If the adjacent node is visited and also on the same path(i.e marked visited in the pathVis array), we will return true, because it means it has a cycle, thereby the pathVis was true. Returning true will mean the end of the function call, as once we have got a cycle, there is no need to check for further adjacent nodes. 
Case 3: If the adjacent node is visited but not on the same path(i.e not marked in the pathVis array), we will continue to the next adjacent node, as it would have been marked as visited in some other path, and not on the current one.
Finally, if there are no further nodes to visit, we will unmark the current node in the pathVis array and just return false. 
Then we will backtrack to the previous node with the returned value.
The point to remember is, while we enter we mark both the pathVis and vis as true, but at the end of traversal to all adjacent nodes, we just make sure we unmark the pathVis and still keep the vis marked as true, as it will avoid future extra traversal calls. 
    */
    public boolean isCyclic(int N, List<List<Integer>> adj) {
        int V=adj.size();
        boolean visited[]=new boolean[V];
        int pathVis[]=new int[V];

        for(int i=0;i<V;i++)
        {
            if(!visited[i]) // remember this if block , this will prevent extra computations
            {
                if(dfsDirected(i,adj,visited,pathVis))
                    return true;
            }
        }
        return false;
    }
    private boolean dfsDirected(int node,List<List<Integer>> adj,boolean visited[],int pathVis[])
    {
        visited[node]=true;
        pathVis[node]=1;
        for(int nbr:adj.get(node))
        {
            if(!visited[nbr]){
                if(dfsDirected(nbr,adj,visited,pathVis)) return true;
            }
            else if(pathVis[nbr]==1)
                return true;
        }
        pathVis[node]=0;
        return false;
    }

    // leetcode 542 01 Matrix 
    // Similar to rotten oranges 
    public int[][] updateMatrix(int[][] mat) {
        int n=mat.length;int m=mat[0].length;
        int ans[][]=new int[n][m];
        boolean vis[][]=new boolean[n][m];
        Queue<int[]> q=new LinkedList<int[]>();
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(mat[i][j]==0)
                {
                    q.offer(new int[]{i,j,0});
                    vis[i][j]=true;
                    ans[i][j]=0;
                }
            }
        }
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        while(!q.isEmpty())
        {
            int sh[]=q.poll();
            int row=sh[0];int col=sh[1];int dist=sh[2];

            ans[row][col]=dist;
            for(int d=0;d<dRow.length;d++)
            {
                int r=row+dRow[d];
                int c=col+dCol[d];
                if(r>=0 &&r<n && c>=0 && c<m && !vis[r][c])
                {
                    if(mat[r][c]==1)
                    {
                        vis[r][c]=true;
                        q.offer(new int[]{r,c,dist+1});
                    }
                }
            }
        }

        return ans;
    }

    // Leetcode 200 Number of islands 
    /*
    Intuition : Dfs here because we need to go to every neighbour as all neighbours conected make one island
    For each row and col index do dfs from there
    if i and j are outside bounds and grid[i][j] is not water then return ie not '0'
    for every dfs call mark this as visited by making it '0' from '1' otherwise subsequent calls in the for loop will consider visisted values again
    post that do dfs on all 4 neigbours 

    for every succesful dfs completed one island has been discovered hence increment counter
    */
    public int numIslands(char[][] grid) {
        int island=0;
        int n=grid.length;int m=grid[0].length;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]=='1')
                {
                    dfs(grid,i,j);
                    island++;
                }
            }
        }
        return island;

    }
    private void dfs(char[][] grid, int i, int j)
    {
        if(i>=grid.length || i<0 || j>=grid[0].length || j<0 || grid[i][j]=='0') // impo conditions to check otherwise infinite loop
            return;
        grid[i][j]='0';// mark this as visited

        dfs(grid,i+1,j);
        dfs(grid,i-1,j);
        dfs(grid,i,j+1);
        dfs(grid,i,j-1);
    }

    // lc 130 Surronded regions 
    /*
    The problem is about converting all 'O' regions that are completely surrounded by 'X' into 'X'. However, any 'O' that is connected to the boundary cannot be converted, since it's not fully surrounded.
So the key idea is:

Mark all 'O's that are connected to the boundary as safe.
At the end, flip all other 'O's (unvisited ones) into 'X'.
Approach
Use DFS (or BFS) starting from boundary cells. Whenever we see an 'O' on the boundary, perform DFS/BFS to mark all connected 'O's as visited (safe).
After this traversal, all boundary-connected 'O's remain as they are, because they cannot be surrounded.
Traverse the entire matrix:
If an 'O' is visited, leave it as 'O'.
If an 'O' is not visited, convert it into 'X'.
    */
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        // visited matrix to mark all 'O's connected to the boundary
        boolean[][] vis = new boolean[m][n];

        // 1. DFS from first/last columns
        for(int i =0;i<m;i++){
            if(board[i][0] == 'O' && vis[i][0] == false){
                dfs(i,0,board,vis);
            }
            if(board[i][n-1] == 'O' && vis[i][n-1] == false){
                dfs(i,n-1,board,vis);
            }
        }
        // 2. DFS from first/last rows
        for(int j = 0;j<n;j++){
            if(board[0][j] == 'O' && vis[0][j] == false){
                dfs(0,j,board,vis);
            }
            if(board[m-1][j] == 'O' && vis[m-1][j] == false){
                dfs(m-1,j,board,vis);
            }
        }

        // 3. Flip surrounded 'O's
        for(int i =0;i<m;i++){
            for(int j =0;j<n;j++){
                // Flip 'O' if it's not marked as visited (i.e., not connected to border)
                if(board[i][j] == 'O' && vis[i][j] == false){
                    board[i][j] = 'X';
                }
            }
        }
    }

    public void dfs(int i, int j, char[][] board, boolean[][] vis){ // dfs here is similar to leetcode 200
        int m = board.length;
        int n = board[0].length;
        // Base case: out of bounds, already visited, or 'X'
        if(i<0 || j<0 || i>=m || j>=n || vis[i][j] == true || board[i][j] == 'X'){
            return;
        }
        vis[i][j] = true;
        // DFS in all 4 directions
        dfs(i+1,j,board,vis);
        dfs(i,j+1,board,vis);
        dfs(i-1,j,board,vis);
        dfs(i,j-1,board,vis);
    }

    // lc 785 check if graph is bipartite or not
    /*
    For DFS traversal, we need a start node and a visited array but in this case, instead of a visited array, we will take a colour array where all the nodes are initialised to -1 indicating they are not coloured yet.
In the DFS function call, make sure to pass the value of the assigned colour, and assign the same in the colour array. We will try to colour with 0 and 1, but you can choose other colours as well. We will start with the colour 0, you can start with 1 as well, just make sure for the adjacent node, it should be opposite of what the current node has. 
In DFS traversal, we travel in-depth to all its uncoloured neighbours using the adjacency list. For every uncoloured node, initialise it with the opposite colour to that of the current node.
If at any moment, we get an adjacent node from the adjacency list which is already coloured and has the same colour as the current node, we can say it is not possible to colour it, hence it cannot be bipartite. Thereby return a false indicating the given graph is not bipartite; otherwise, keep on returning true.
    */
    public boolean isBipartite(int[][] graph) {
        int V=graph.length;
        int color[]=new int[V];
        for(int i=0;i<V;i++) 
            color[i]=-1;

        for(int i=0;i<V;i++) 
        {
            if(color[i]==-1)
            {
                if(dfsBipartite(graph,color,i,0)==false)
                    return false; // returning false because code may early exit so no need of checking again and again
            }
        }
        return true;
        
    }
    private boolean dfsBipartite(int[][] graph,int color[],int i,int col)
    {
        color[i]=col;
        for(int nbr:graph[i])
        {
            if(color[nbr] == -1) {
                if(dfsBipartite(graph,color,nbr,1-col)==false) 
                    return false; 
            }
            else if(color[i]==color[nbr])
                return false;
        }
        return true;
    }

    // lc 1020
    /*
    Intuition
    Initialize a queue for BFS and a vis array to track visited cells.
Traverse the boundary of the grid. For every boundary land cell (grid[i][j] == 1), mark it visited and push it into the queue.
Run BFS from these boundary land cells:
At each step, pop a cell and explore its 4 neighbors (up, right, down, left).
If the neighbor is land and not yet visited, mark it visited and push it into the queue.
After BFS, iterate over the entire grid. Any land cell (grid[i][j] == 1) that remains unvisited is an enclave.
Count these enclaves and return the result.
    */
    public int numEnclaves(int[][] grid) {
        Queue<Pair> q=new LinkedList<>();
        int n=grid.length;int m=grid[0].length;
        boolean vis[][]=new boolean[n][m];

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if((i==0 || j==0 || i==n-1 || j==m-1))
                {
                    if(grid[i][j]==1)
                    {
                        q.offer(new Pair(i,j));
                        vis[i][j]=true;
                    }
                }
            }
        }
        int[] dRow = {-1, 1, 0, 0}; // helping in direction traversal 
        int[] dCol = {0, 0, -1, 1};
        while(!q.isEmpty())
        {
            Pair p=q.poll();
            int i=p.a;int j=p.b;
            for(int d=0;d<4;d++)
            {
                int r=i+dRow[d];
                int c=j+dCol[d];
                if(r<n && r>=0 && c<m && c>=0 && grid[r][c]==1)
                {
                    if(!vis[r][c]) {
                        vis[r][c]=true;
                        q.offer(new Pair(r,c));
                    }
                }
            }

        }

        int ans=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==1 && !vis[i][j])
                {
                   ans++;
                }
            }
        }
        return ans;

    }
    // lc 127 word ladder
    /*
        intuition
            Breadth-First Search (BFS) ensures the shortest path is found from startWord to targetWord.
    Initialize a queue with a pair {startWord, 1} representing the word and its current transformation steps.
    Insert all words from wordList into a unordered_set for O(1) lookups.
    While the queue is not empty:
    Pop a word and its step count.
    If this word is the targetWord, return the step count.
    For each character in the word, try replacing it with all letters 'a' to 'z':
        If the new word exists in the set, erase it from the set and push it into the queue with steps + 1.
    If no transformation sequence is found, return 0.

    */
    class Pair2<K, V> {
        private K key;
        private V value;
        public Pair2(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() { return key; }
        public V getValue() { return value; }
    }
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<Pair2<String, Integer>> q = new LinkedList<>();
        q.add(new Pair2<>(beginWord, 1));
        Set<String> st = new HashSet<>(wordList);
        st.remove(beginWord);
        while (!q.isEmpty()) {
            String word = q.peek().getKey();
            int steps = q.peek().getValue();
            q.poll();

            // If target word is found
            if (word.equals(endWord)) return steps;

            // Try changing every character
            for (int i = 0; i < word.length(); i++) {
                char[] arr = word.toCharArray();
                char original = arr[i];
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    arr[i] = ch;
                    String newWord = new String(arr);
                    if (st.contains(newWord)) {
                        st.remove(newWord);
                        q.add(new Pair2<>(newWord, steps + 1));
                    }
                }
                arr[i] = original;
            }
        }
        return 0;
    }

    // topological sort using dfs
    /*
    definition : Topological sorting is a linear ordering of vertices in a Directed Acyclic Graph (DAG) such that for every directed edge from a vertex u to a vertex v (u → v), u appears before v in the ordering. Since the graph is acyclic, 
    we can process vertices in a way that ensures dependencies are respected. 
    The DFS-based approach works by exploring each vertex’s outgoing edges fully before marking it as finished. 
    Once a vertex has no unvisited neighbors, we record it. This guarantees that all dependencies of a vertex are placed earlier in the order. 
    At the end, reversing this finishing order yields a valid topological sorting.
    */

    // Tpopological sort can only be done on directed acyclic graph or DAG
    // 1. Directed as if it is undirected 1->2 and 2->1 in the ordering this comes up again 
    // 2. Acylic as if it is cyclic 1->2 and 2->3 and 3->1 in the ordering this comes up again as 1,2,3,1 which is not possible
    // The main aim is to get a valid topological ordering

    void dfsTopo(int node, ArrayList<ArrayList<Integer>> adj, int[] vis, Stack<Integer> st) {
        // Mark the current node as visited
        vis[node] = 1;

        // Explore all neighbors of this node
        for (int it : adj.get(node)) {
            // If the neighbor is not visited, recursively perform DFS
            if (vis[it] == 0) {
                dfsTopo(it, adj, vis, st);
            }
        }
        // After visiting all neighbors, push this node into the stack
        st.push(node);
    }

    // Function to perform Topological Sort
    ArrayList<Integer> topoSort(int V, ArrayList<ArrayList<Integer>> adj) {
        // Create a visited array to mark visited vertices
        int[] vis = new int[V];
        // Stack to store vertices in finishing order
        Stack<Integer> st = new Stack<>();

        // Perform DFS from each unvisited vertex
        for (int i = 0; i < V; i++) {
            if (vis[i] == 0) {
                dfsTopo(i, adj, vis, st);
            }
        }
        // Prepare the result array
        ArrayList<Integer> ans = new ArrayList<>();
        while (!st.isEmpty()) {
            ans.add(st.pop());
        }
        // Return the topological ordering
        return ans;
    }
    // Function to perform BFS-based topological sort, this code is similar to lc 207 course scheduler
    // This is standard topo sort using BFS indegree method is fgor bfs only 
    public int[] topologicalSort(int V, ArrayList<ArrayList<Integer>> adj) {
        // Create an array to store the in-degree of each vertex
        int[] indegree = new int[V];

        // Loop over all vertices to calculate in-degree
        for (int i = 0; i < V; i++) {
            // Loop over all adjacent vertices of current vertex
            for (int it : adj.get(i)) {
                // Increase in-degree of connected vertex
                indegree[it]++;
            }
        }

        // Create a queue to store vertices with in-degree zero
        Queue<Integer> q = new LinkedList<>();

        // Loop through all vertices
        for (int i = 0; i < V; i++) {
            // If in-degree is zero, add it to queue
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        // Array to store the topological order
        int[] topo = new int[V];
        // Index to track position in topo array
        int idx = 0;

        // Process vertices in queue
        while (!q.isEmpty()) {
            // Remove vertex from queue
            int node = q.poll();

            // Add it to the topological order
            topo[idx++] = node;

            // Loop through adjacent vertices of the current node
            for (int it : adj.get(node)) {
                // Reduce in-degree of connected vertex
                indegree[it]--;
                // If in-degree becomes zero, push it to queue
                if (indegree[it] == 0) {
                    q.add(it);
                }
            }
        }

        // Return the topological ordering
        return topo;
    }
    // lc 207

    /*
    Intuition : Kahns Algo BFS Topological sorting 

Imagine every course is locked.
Whenever all its prerequisites disappear,
it becomes unlocked.
If some courses remain locked forever,
they are part of a cycle.
indegree: simply the number of incoming edges
indegree here means the prerequisite count of coursese to be completed befroe taking a particular course
ex : to take course 3 you need to complete course 1 and 2 hence indegree is 2 
    */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            graph.add(new ArrayList<>());

        int[] indegree = new int[numCourses];
        for(int[] edge : prerequisites) // prerequisites is adj matrix and not list hence this is needed
        { // only for this question
            int course = edge[0];
            int prereq = edge[1];

            graph.get(prereq).add(course);
            indegree[course]++;
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<numCourses;i++)
        {
            if(indegree[i]==0)
                q.offer(i);
        }
        int count=0;
        while(!q.isEmpty())
        {
            int node=q.poll();
            count++;
            for(int nbr:graph.get(node))
            {
                indegree[nbr]--;
                if(indegree[nbr]==0)
                    q.offer(nbr);
            }
        }
        return count==numCourses;
    }
    // lc 210 almost the same as the one above
    // diff between both is lc 210 asks you to return the topological sort ordering if possible
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            graph.add(new ArrayList<>());
        int indegree[]=new int[numCourses];
        for(int i[]:prerequisites)
        {
            int course=i[0];
            int pre=i[1];
            graph.get(pre).add(course);
            indegree[course]++;
        }
        int[] topo = new int[numCourses];
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<numCourses;i++)
        {
            if(indegree[i]==0) q.add(i);
        }
        int idx=0;
        while(!q.isEmpty())
        {
            int course=q.poll();
            topo[idx++]=course;
            for(int nbr:graph.get(course))
            {
                indegree[nbr]--;
                if(indegree[nbr]==0)
                {
                    q.add(nbr);
                }
            }
        }
        if (idx == numCourses) {
            return topo;
        }
        return new int[0];
    }

    // lc 802
    /*
    Step 1: Reverse all edges of the graph so we can apply topological sort based on indegrees.
    Step 2: Create an indegree[] array initialized to 0. Count the incoming edges for each node and store them accordingly.
    Step 3: Initialize a queue and enqueue all nodes with indegree 0 — these are now our terminal nodes in the reversed graph.
    Step 4: Create an empty list safeNodes[] to store all safe nodes.
    Step 5: While the queue is not empty:
        Pop a node u from the queue and add it to safeNodes.
        For each neighbor v of node u in the reversed graph, reduce indegree[v] by 1.
        If indegree[v] becomes 0, push it into the queue.
    Step 6: Repeat this process until the queue is empty.
    Step 7: The safeNodes list now contains all the nodes that are not part of any cycle and do not connect into one.
    Step 8: Sort the safeNodes array before returning it, as the problem requires output in ascending order.
    */

    public List<Integer> eventualSafeNodes(int[][] graph) {
        int V=graph.length;
        List<Integer>[] adjRev = new List[V];  // Reverse adjacency list
        int[] indegree = new int[V];  // Indegree array to track nodes with no outgoing edges

        // Initialize reverse adjacency list
        for (int i = 0; i < V; i++) {
            adjRev[i] = new ArrayList<>();
        }

        // Build the reverse graph and calculate indegrees
        for (int i = 0; i < V; i++) {
            for (int neighbor : graph[i]) {
                adjRev[neighbor].add(i);  // Reverse the direction of edges
                indegree[i]++;  // Increment indegree for the current node
            }
        }

        Queue<Integer> q = new LinkedList<>();  // Queue to store nodes with no outgoing edges
        List<Integer> safeNodes = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // Process the queue to find all safe nodes
        while (!q.isEmpty()) {
            int node = q.poll();
            safeNodes.add(node);  // This node is safe
            for (int parent : adjRev[node]) {
                indegree[parent]--;  // Decrease indegree of the parent nodes
                if (indegree[parent] == 0) {
                    q.offer(parent);  // If indegree becomes 0, it is a safe node
                }
            }
        }

        Collections.sort(safeNodes);  // Sort the safe nodes
        return safeNodes;
    }

    // Alien Dictionary https://takeuforward.org/data-structure/alien-dictionary-topological-sort-g-26

    public String findOrder(String [] dict, int N, int K) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < K; i++) {
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<N-1;i++)
        {
            String dic1=dict[i];
            String dic2=dict[i+1];
            int len = Math.min(dic1.length(), dic2.length());
            for(int j=0;j<len;j++)
            {
                if(dic1.charAt(j) !=dic2.charAt(j) )
                {
                    // Add edge s1[ptr] -> s2[ptr]
                    adj.get(dic1.charAt(j) - 'a').add(dic2.charAt(j) - 'a');
                    break;
                }
            }
        }
        List<Integer> topo = topoSort(K, adj); // normal topo sort
        // Convert numeric representation back to characters
        StringBuilder ans = new StringBuilder();
        for (int node : topo) {
            ans.append((char)(node + 'a'));
        }
        return ans.toString();
    }


    // new Pattern shortest path from src to dest using BFS/DFS
    // the below is Shortest Path in Undirected Graph with unit distance 
    // This is the exact same bfs but instead of having a visisted we have a distance array to keep track of the shortest path from src to that particukar node
    // If node is not reachable form source then the dist[node] will be infinity
    public int[] shortestPath(int[][] edges, int N, int M, int src) {

        // Create adjacency list for undirected graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }

        // Fill adjacency list from edge list
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // Initialize distance array with large value (infinity)
        int[] dist = new int[N];
        Arrays.fill(dist, (int) 1e9); // or -1 or Integer.MAX_VALUE

        // Set source distance to 0
        dist[src] = 0;

        // Initialize queue for BFS
        Queue<Integer> q = new LinkedList<>();
        q.add(src);

        // BFS traversal
        while (!q.isEmpty()) {
            int node = q.poll();

            // Traverse neighbors
            for (int neighbor : adj.get(node)) {
                if (dist[node] + 1 < dist[neighbor]) {
                    dist[neighbor] = dist[node] + 1;// updating the distance based on the shorter value 
                    q.add(neighbor);
                }
            }
        }

        // Prepare result, replacing infinity with -1
        for (int i = 0; i < N; i++) {
            if (dist[i] == (int) 1e9) {
                dist[i] = -1;
            }
        }

        return dist;
    }

    // Topological sorting in a DAG directed acyclic graph only is used to find the shortest path to a dist
    // uses dfs hence stack is used
    private void topoSort(int node, List<List<int[]>> adj, boolean[] visited, Stack<Integer> stack) {
        
        // Mark the current node as visited
        visited[node] = true;
        
        // Traverse all adjacent nodes
        for (int[] neighbor : adj.get(node)) {
            
            // If the neighbor hasn't been visited, recurse
            if (!visited[neighbor[0]]) {
                topoSort(neighbor[0], adj, visited, stack);
            }
        }
        
        // After visiting all neighbors, push this node into the stack
        stack.push(node);
    }

    // Function to compute shortest path from source node (0) in a DAG
    public int[] shortestPath(int N, int M, int[][] edges) { // almost same as the shortest path in undirected graph in bfs implementation above 
        // diff is that edges have weights also 
        
        // Create adjacency list for graph with weights
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        
        // Fill the adjacency list from edge list
        for (int i = 0; i < M; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int wt = edges[i][2];
            adj.get(u).add(new int[]{v, wt});
        }

        // Initialize visited array for topo sort
        boolean[] visited = new boolean[N];
        // Stack to hold topological order
        Stack<Integer> stack = new Stack<>();
        
        // Perform topo sort from unvisited nodes
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                topoSort(i, adj, visited, stack);
            }
        }

        // Initialize distance array with infinity (represented by large value)
        int[] dist = new int[N];
        Arrays.fill(dist, (int)1e9);

        // Distance to source (0) is 0
        dist[0] = 0;

        // Process nodes in topological order
        while (!stack.isEmpty()) {
            int node = stack.pop();
            
            // If the node is reachable
            if (dist[node] != (int)1e9) {
                
                // Traverse all neighbors and update their distances
                for (int[] neighbor : adj.get(node)) {
                    int v = neighbor[0];
                    int wt = neighbor[1];
                    
                    // Relax the edge
                    if (dist[node] + wt < dist[v]) {
                        dist[v] = dist[node] + wt;
                    }
                }
            }
        }
        // Replace all unreachable nodes with -1
        for (int i = 0; i < N; i++) {
            if (dist[i] == (int)1e9) {
                dist[i] = -1;
            }
        }
        // Return the final distance array
        return dist;
    }
}
