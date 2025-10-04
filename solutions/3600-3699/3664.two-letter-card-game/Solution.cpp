class Solution {
public:
    int score(vector<string>& cards, char x) {
        unordered_map<char,int> leftMap,rightMap,bothMap;
        for(auto str:cards)
        {
            if(str[0]==x && str[1]==x)
            bothMap[str[0]]++;
            else if(str[0]==x)
            rightMap[str[1]]++;
            else if(str[1]==x)
            leftMap[str[0]]++;
        }
        vector<int> left,right;
        for(auto xx:leftMap)
        left.push_back(xx.second);
        for(auto xx:rightMap)
        right.push_back(xx.second);

        sort(left.rbegin(),left.rend());
        sort(right.rbegin(),right.rend());

        int rightFirst=0,rightSum=0,rightN=right.size(),leftFirst=0,leftN=left.size(),leftSum=0;
        if(rightN>0)
        {
            rightFirst=right[0];
            for(int i=1;i<rightN;i++)
            rightSum+=right[i];
        }
        if(leftN>0)
        {
            leftFirst=left[0];
            for(int i=1;i<leftN;i++)
            leftSum+=left[i];
        }

        int ans=0,count=bothMap[x];
        for(int i=0;i<=count;i++)
        {
            int p1=0;
            if(i+leftSum>=leftFirst)
            p1=(leftFirst+leftSum+i)/2;
            else
            p1=min(leftFirst,leftSum+i);
            int p2=0;
            if(count-i+rightSum>=rightFirst)
            p2=(rightFirst+rightSum+count-i)/2;
            else
            p2=min(rightFirst,rightSum+count-i);
            ans=max(ans,min(leftFirst+leftSum,p1)+ min(rightFirst+rightSum,p2));
        }
        return ans;
    }
};