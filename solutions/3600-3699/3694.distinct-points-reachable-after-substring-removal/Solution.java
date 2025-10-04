public class Node{
    int x,y;
    public Node(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
}
class Solution {

    public int distinctPoints(String s, int k) {
        if(s.length()==k)
        {
            return 1;
        }
        HashSet<String> hset=new HashSet<String>();
        Node pref[]=new Node[s.length()];
        int a=0,b=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='L')
            {
                a=a-1;
            }
            else if(s.charAt(i)=='U')
            {
                b=b+1;
            }
            else if(s.charAt(i)=='D')
            {
                b=b-1;
            }
            else
            {
                a=a+1;
            }

            pref[i]=new Node(a,b);
        }

        int j=-1;
        Node l=pref[pref.length-1];
        for(int i=k;i<=s.length();i++)
        {
            Node p=pref[i-1];
            if(j==-1)
            {
               hset.add(Integer.toString(l.x-p.x)+" "+Integer.toString(l.y-p.y));
            }
            else
            {
              hset.add(Integer.toString(l.x-p.x+pref[j].x)+" "+Integer.toString(l.y-p.y+pref[j].y));
            }
            j++;
        }

        return hset.size();
    }
}