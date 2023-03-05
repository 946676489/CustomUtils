package test;

public class Solution1 {

	public ListNode removeZeroSumSublists(ListNode head) {
		return remove0(head);

	}
	ListNode remove0(ListNode node) {
		int sum = 0;
		ListNode head = node;
		ListNode n = node;
		ListNode begin = node;
		while (n != null) {
			if (n.val == 0) {
				head = removeNode(head, n, n);
				n = n.next;
				begin = head;
				continue;
			}
			sum += n.val;
			if (sum == 0) {
				head = removeNode(head, begin, n);
				if (head == null) {
					return null;
				}
				begin = n.next;
			}
			n = n.next;
		}
		if (head != null && head.next != null) {
			head.next = remove0(head.next);
		}
		return head;
	}
	private ListNode removeNode(ListNode head, ListNode begin, ListNode end) {
		if (begin == head) {
			return end.next;
		}
		ListNode pre = head;
		ListNode n = head.next;
		while (n != null) {
			if (n == begin) {
				pre.next = end.next;
				break;
			}
			n = n.next;
			pre = pre.next;
		}
		return head;
	}
}
