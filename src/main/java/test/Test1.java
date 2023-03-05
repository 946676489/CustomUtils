package test;

import java.util.StringJoiner;

public class Test1 {

	private static final String SS = "0,1,-1";

	public static void main(String[] args) {
		String[] arr = SS.split(",");
		ListNode head = null;
		ListNode n = null;
		for (int i = 0; i < arr.length; i++) {
			ListNode nd = n;
			n = new ListNode();
			n.val = Integer.parseInt(arr[i]);
			if (head != null) {
				nd.next = n;
			} else {
				head = n;
			}
		}
		ListNode node = new Solution1().remove0(head);
		if(node!=null) {
			n = node;
			StringJoiner sj = new StringJoiner(",","[","]");
			while (n!=null) {
				sj.add(String.valueOf(n.val));
				n = n.next;
			}
			System.out.println(sj);
		} else {
			System.out.println("[]");
		}

	}

}
class ListNode {
	int val;
	ListNode next;
	ListNode() {
	}
	ListNode(int val) {
		this.val = val;
	}
	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}