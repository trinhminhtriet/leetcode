/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode p = list1, q = list1;
        while (--a > 0) {
            p = p.next;
        }
        while (b-- > 0) {
            q = q.next;
        }
        p.next = list2;
        while (p.next != null) {
            p = p.next;
        }
        p.next = q.next;
        q.next = null;
        return list1;
    }
}