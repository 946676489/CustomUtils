package test;

public class TestRain {

	public static void main(String[] args) {
		int[] hs = {5,4,1,2};
		int trap = new TestRain().trap(hs);
		System.out.println(trap);
	}

	public int trap(int[] height) {
		return rain(height, 0);
	}

	private int rain(int[] hs, int start) {
		if (hs.length < start + 3) {
			return 0;
		}
		if (hs[start] <= hs[start + 1]) {
			return rain(hs, start + 1);
		}
		int amount = 0;
		int n = start + 2;
		while (n < hs.length && hs[n] < hs[start]) {
			n++;
		}
		if (n == hs.length && hs[n - 1] < hs[start]) {
			int len = hs.length - start;
			int[] arr = new int[len];
			System.arraycopy(hs, start, arr, 0, len);
			reverse(arr);
			return rain(arr, 0);
		}
		for (int i = start + 1; i < n; i++) {
			amount += hs[start] - hs[i];
		}
		return amount + rain(hs, n);
	}

	private void reverse(int[] arr) {
		int n = arr.length / 2;
		for (int i = 0; i < n; i++) {
			int t = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = t;
		}

	}

}
