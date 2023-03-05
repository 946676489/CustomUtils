package test;

public class Solution {

	private boolean b = false;
	public ListNode removeZeroSumSublists(ListNode head) {
		return remove0(head);

	}
	ListNode remove0(ListNode node) {
		if (node.next == null) {
			return proc0(node);
		}
		if (node.val == 0) {
			if (node.next == null) {
				return null;
			} else {
				return remove0(node.next);
			}
		}
		ListNode head = node;
		int res = head.val;
		ListNode n = remove0(node.next);
		if (n == null) {
			head.next = null;
			return proc0(head);
		}
		if (b) {
			head.next = n;
			b = false;
		}
		boolean h = true;
		while (n != null) {
			res += n.val;
			if (res == 0) {
				if (h) {
					head = n.next;
					if (head == null) {
						return head;
					}
					res = head.val;
					n = head;
					h = false;
				} else {
					head.next = n.next;
				}
				b = true;
			}
			n = n.next;
		}
		return proc0(head);
	}
	private ListNode proc0(ListNode head) {
		if (head.next == null && head.val == 0) {
			return null;
		}
		return head;
	}

}
