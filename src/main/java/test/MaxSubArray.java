//请关闭中文输入法，用英文的字母和标点符号。
// 如果你想运行系统测试用例，请点击【执行代码】按钮，如果你想提交作答结果，请点击【提交】按钮，
// 注意：除答案外，请不要打印其他任何多余的字符，以免影响结果验证
// 本OJ系统是基于 OxCoder 技术开发，网址：www.oxcoder.com
// 模版代码提供基本的输入输出框架，可按个人代码习惯修改

package test;

import java.util.ArrayList;
import java.util.List;

import util.FileUtils;
public class MaxSubArray {

	public static void main(String[] args) {
		List<String> lns = FileUtils.readLines("src/main/java/test/enter.txt", "UTF-8");

		String s = lns.get(0);

		String[] arr_temp = s.split("\\s");

		int m = Integer.parseInt(arr_temp[0]);
		int n = Integer.parseInt(arr_temp[1]);

		ArrayList<ArrayList<Integer>> vector = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			String str_2 = lns.get(i + 1);
			String[] line_list_2 = str_2.trim().split(" ");
			ArrayList<Integer> temp_2 = new ArrayList<>();
			for (int j = 0; j < line_list_2.length; j++) {
				temp_2.add(Integer.parseInt(line_list_2[j]));
			}
			vector.add(temp_2);
		}

		int result = solution(m, n, vector);

		System.out.println(result);

	}

	public static int solution(int m, int n, ArrayList<ArrayList<Integer>> vector) {
		int result = 0;

		for (int i = 0; i < vector.size(); i++) {
			ArrayList<Integer> ls = vector.get(i);
			if (i > vector.size() - 2) {
				continue;
			}
			for (int j = 0; j < ls.size(); j++) {
				if (j == ls.size() - 2) {
					break;
				}
				result = Math.max(maxVal(vector, i, j, n), result);
			}
		}

		return result;
	}
	private static int maxVal(ArrayList<ArrayList<Integer>> vector, int i, int j, int n) {
		int res = 0, sum = 0, x = (vector.size() - i - 1) * (n - j - 1);
		int w = 2, h = 2;

		while (x > 0) {
			for (int k = 0; k < h; k++) {
				ArrayList<Integer> ls = vector.get(i + k);
				for (int l = 0; l < w; l++) {
					sum += ls.get(j + l);
				}
			}
			res = Math.max(sum, res);
			sum = 0;
			x--;
			if (w < n - j) {
				w++;
			} else {
				w = 2;
				h++;
			}
		}

		return res;
	}
}