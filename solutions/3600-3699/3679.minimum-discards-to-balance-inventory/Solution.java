class Solution {
    public int minArrivalsToDiscard(int[] arrivals, int w, int m) {
        int count=0;
         HashMap<Integer,Integer> h=new HashMap<Integer,Integer>();
         HashSet<Integer> hset=new HashSet<Integer>();
        for(int i=0;i<w;i++)
        {
            
            if(!h.containsKey(arrivals[i]))
            {
                hset.add(i);
                h.put(arrivals[i],1);
            }
            else
            {
                 if(h.get(arrivals[i])+1<=m)
                {
                    h.put(arrivals[i],h.get(arrivals[i])+1);
                    hset.add(i);
                }
                else
                {
                    count++;
                }
            }
        }
        int x=0;
        for(int i=w;i<arrivals.length;i++)
        {
            if(hset.contains(x))
            {
                if(h.get(arrivals[x])==1)
                {
                    h.remove(arrivals[x]);
                }
                else
                {
                    h.put(arrivals[x],h.get(arrivals[x])-1);
                }
            }
            if(h.containsKey(arrivals[i]))
            {
                if(h.get(arrivals[i])+1<=m)
                {
                    h.put(arrivals[i],h.get(arrivals[i])+1);
                    hset.add(i);
                }
                else
                {
                    count++;
                }
            }
            else
            {
                h.put(arrivals[i],1);
                hset.add(i);
            }
            x++;
            
        }

        return count;
    }
}