package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		while ((str = br.readLine()) != null) {
			if ("0".equals(str)) {
				return;
			}
			System.out.println(calculateCnt(0, Integer.parseInt(str)));
		}

		br.close();
	}

	private static int calculateCnt(int cnt, int bottles) {
		int n = bottles / 3;
		int mod = bottles % 3;
		if(n==0) {
			if(mod==2) {
				return cnt+1;
			} else {
				return cnt;
			}
		}
		return calculateCnt(n + cnt, n + mod);
	}
}