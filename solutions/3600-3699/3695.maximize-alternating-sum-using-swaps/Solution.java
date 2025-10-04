class Solution {
    int[] parent;
    int [] rank ;
    //to find the parent of node x
    int find(int x){
        if(x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }
    //combine x and y into single component
    void union(int x,int y){
        int a= find(x);
        int b = find(y);
        if(a == b) return ;
        if(rank[a]>rank[b]){
            parent[b] = a;
        }
        else if(rank[b]>rank[a]){
            parent[a]=b;
        }
        else {
            parent[a] = b;
            rank[b]++;
        }
    }
    public long maxAlternatingSum(int[] nums, int[][] swaps) {
        int n = nums.length;
        parent = new int[n+1];
        rank = new int[n+1];
        //each node is its own parent
        for(int i = 0;i<=n;i++){
            parent[i] = i;
        }
        //assume swap as node (x,y)
        for(int[] e:swaps){
            union(e[0],e[1]);
        }
        HashMap<Integer,PriorityQueue<Integer>> map = new HashMap<>();
        long sum = 0;
        //map all  componenets to its parent
        //within each component we can swap any two elements as nedded
        //so we can sort the elemnts in each component
        //and use largest elemnts in component as even indices.
        //adn remaining as odd indices
        for(int i = 0;i<n;i++){
            sum += nums[i];
            int p = find(i);
            if(!map.containsKey(p)){
                map.put(p,new PriorityQueue<Integer>((a,b)->b-a));
            }
            map.get(p).offer(nums[i]);
        }
        long even = 0;
        //greedily select the best nodes as even indices
        for(int i = 0;i<n;i+=2){
            int p = find(i);
            even += map.get(p).poll();
        }
        long odd = sum-even;
        return even-odd;
    }
}