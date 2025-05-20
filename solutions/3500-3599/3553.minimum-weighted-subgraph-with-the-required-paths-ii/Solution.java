import java.util.*;

class Solution {
    public int[] minimumWeight(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            adj[u].add(new int[] { v, w });
            adj[v].add(new int[] { u, w });
        }

        // DSU setup
        int[] parent = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // DSU find
        Function<Integer, Integer> findSet = new Function<>() {
            @Override
            public Integer apply(Integer v) {
                while (parent[v] != v) {
                    parent[v] = parent[parent[v]];
                    v = parent[v];
                }
                return v;
            }
        };

        // DSU union
        BiFunction<Integer, Integer, Integer> unionSets = (a, b) -> {
            a = findSet.apply(a);
            b = findSet.apply(b);
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
            return a;
        };

        // Index queries
        List<int[]>[] queriesByV = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            queriesByV[i] = new ArrayList<>();
        }

        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0], b = queries[i][1], c = queries[i][2];
            queriesByV[a].add(new int[] { b, c, i });
            queriesByV[b].add(new int[] { c, a, i });
            queriesByV[c].add(new int[] { a, b, i });
        }

        boolean[] visited = new boolean[n];
        int[] ancestor = new int[n];
        for (int i = 0; i < n; i++) {
            ancestor[i] = i;
        }

        int[] dist = new int[n];
        int[] answer = new int[queries.length];

        // DFS
        dfs(0, adj, visited, dist, ancestor, parent, size, queriesByV, answer, findSet, unionSets);

        return answer;
    }

    private void dfs(int v, List<int[]>[] adj, boolean[] visited, int[] dist, int[] ancestor,
            int[] parent, int[] size, List<int[]>[] queriesByV, int[] answer,
            Function<Integer, Integer> findSet,
            BiFunction<Integer, Integer, Integer> unionSets) {

        visited[v] = true;

        for (int[] query : queriesByV[v]) {
            int b = query[0], c = query[1], i = query[2];
            answer[i] += dist[v];
            if (visited[b]) {
                answer[i] -= dist[ancestor[findSet.apply(b)]];
            }
            if (visited[c]) {
                answer[i] -= dist[ancestor[findSet.apply(c)]];
            }
        }

        for (int[] neighbor : adj[v]) {
            int u = neighbor[0], w = neighbor[1];
            if (visited[u])
                continue;

            dist[u] = dist[v] + w;
            dfs(u, adj, visited, dist, ancestor, parent, size, queriesByV, answer, findSet, unionSets);
            ancestor[unionSets.apply(v, u)] = v;
        }
    }
}
