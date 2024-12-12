// OJ: https://leetcode.com/contest/weekly-contest-251/problems/delete-duplicate-folders-in-system/
// Author: github.com/lzl124631x
// Time: O(NDWlogC + N^2 * W) where `N` is the number of folders, `W` is the maximum length of folder name, 
//                                  `D` is the deepest folder depth, and `C` is the maximum number of direct child folders.
// Space: O(N^2 * W)
struct Node {
    string name;
    map<string, Node*> next; // mapping from folder name to the corresponding child node.
    bool del = false; // whether this folder is deleted.
    Node(string n = "") : name(n) {}
};
class Solution {
    void addPath(Node *node, vector<string> &path) { // Given a path, add nodes to the folder tree. This is similar to the Trie build process.
        for (auto &s : path) {
            if (node->next.count(s) == 0) node->next[s] = new Node(s);
            node = node->next[s];
        }
    }
    unordered_map<string, Node*> seen; // mapping from subfolder structure string to the first occurrence node.
    string dedupe(Node *node) { // post-order traversal to dedupe. If we've seen the subfolder structure before, mark it as deleted.
        string subfolder;
        for (auto &[name, next] : node->next) {
            subfolder += dedupe(next);
        }
        if (subfolder.size()) { // leaf nodes should be ignored
            if (seen.count(subfolder)) { // if we've seen this subfolder structure before, mark them as deleted.
                seen[subfolder]->del = node->del = true;
            } else {
                seen[subfolder] = node; // otherwise, add the mapping
            }
        }
        return "(" + node->name + subfolder + ")"; // return the folder structure string of this node.
    }
    vector<vector<string>> ans;
    vector<string> path;
    void getPath(Node *node) {
        if (node->del) return; // if the current node is deleted, skip it.
        path.push_back(node->name);
        ans.push_back(path);
        for (auto &[name, next] : node->next) {
            getPath(next);
        }
        path.pop_back();
    }
public:
    vector<vector<string>> deleteDuplicateFolder(vector<vector<string>>& A) {
        Node root;
        for (auto &path : A) addPath(&root, path);
        dedupe(&root);
        for (auto &[name, next] : root.next) getPath(next);
        return ans;
    }
};