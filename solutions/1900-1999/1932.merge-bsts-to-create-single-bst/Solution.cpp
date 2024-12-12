class Solution {
public:
    TreeNode* canMerge(vector<TreeNode*>& trees) {
        unordered_map<int, int> freq; 
        stack<TreeNode*> stk; 
        
        for (auto& tree : trees) {
            stk.push(tree); 
            while (stk.size()) {
                TreeNode* node = stk.top(); stk.pop(); 
                if (node) {
                    ++freq[node->val]; 
                    stk.push(node->left); 
                    stk.push(node->right); 
                }
            }
        }
        
        int cnt = 0; 
        TreeNode* root = NULL; 
        unordered_map<int, TreeNode*> mp; 
        
        for (auto& tree : trees) {
            mp[tree->val] = tree; 
            if (freq[tree->val] & 1) {
                ++cnt; 
                root = tree; 
            }
        }
        
        if (cnt != 1) return NULL; 
        
        stk.push(root); 
        int total = trees.size(); 
        while (stk.size()) {
            TreeNode* node = stk.top(); stk.pop(); 
            if (node->left && !node->left->left && !node->left->right && mp.count(node->left->val)) {
                node->left = mp[node->left->val]; 
                --total; 
            }
            if (node->right && !node->right->left && !node->right->right && mp.count(node->right->val)) {
                node->right = mp[node->right->val]; 
                --total; 
            }
            if (node->left) stk.push(node->left); 
            if (node->right) stk.push(node->right); 
        }
        
        if (total != 1) return NULL; 
        
        int prev = INT_MIN; 
        TreeNode* node = root; 
        while (stk.size() || node) {
            if (node) {
                stk.push(node); 
                node = node->left; 
            } else {
                node = stk.top(); stk.pop(); 
                if (prev >= node->val) return NULL; 
                prev = node->val; 
                node = node->right; 
            }
        }
        return root; 
    }
};