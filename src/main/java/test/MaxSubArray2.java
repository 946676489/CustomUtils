//请关闭中文输入法，用英文的字母和标点符号。
// 如果你想运行系统测试用例，请点击【执行代码】按钮，如果你想提交作答结果，请点击【提交】按钮，
// 注意：除答案外，请不要打印其他任何多余的字符，以免影响结果验证
// 本OJ系统是基于 OxCoder 技术开发，网址：www.oxcoder.com
// 模版代码提供基本的输入输出框架，可按个人代码习惯修改

package test;

import java.util.ArrayList;
import java.util.Scanner;
public class MaxSubArray2 {

	    public static void main(String[] args) {
	        Scanner scan = new Scanner(System.in);
	        
	        String str_0 = scan.nextLine();
	        String[] line_list_0 = str_0.trim().split(" ");        
	        ArrayList<Long> arr_temp = new ArrayList<>();
	        for(int i = 0; i < line_list_0.length; i++){
	            arr_temp.add(Long.parseLong(line_list_0[i]));
	        }
	    

	        long m = arr_temp.get(0);
			long n = arr_temp.get(1);

	        
	        ArrayList<ArrayList<Long>> vector = new ArrayList<>();
	        for(int i = 0; i < m; i++){
	            String str_2 = scan.nextLine();
	            String[] line_list_2 = str_2.trim().split(" ");        
	            ArrayList<Long> temp_2 = new ArrayList<>();
	            for(int j = 0; j < line_list_2.length; j++){
	                temp_2.add(Long.parseLong(line_list_2[j]));
	            }
	            vector.add(temp_2);
	        }
	    

	        scan.close();

	        long result = solution(m, n, vector);

	        System.out.println(result);

	    }

	    public static long solution(long m, long n, ArrayList<ArrayList<Long>> vector) {
			long result = 0;

			for (int i = 0; i < vector.size(); i++) {
				ArrayList<Long> ls = vector.get(i);
				if (i > vector.size() - 1) {
					continue;
				}
				for (int j = 0; j < ls.size(); j++) {
					if (j == ls.size() - 1) {
						break;
					}
					result = Math.max(maxVal(vector, i, j, n), result);
				}
			}

			return result;
		}
		private static long maxVal(ArrayList<ArrayList<Long>> vector, int i, int j, long n) {
			long res = 0, sum = 0, x = (vector.size() - i - 1) * (n - j - 1);
			int w = 2, h = 2;

			while (x > 0) {
				for (int k = 0; k < h; k++) {
					ArrayList<Long> ls = vector.get(i + k);
					for (int l = 0; l < w; l++) {
						System.out.print(ls.get(j+l)+" ");
						sum += ls.get(j + l);
					}
				}
				System.out.println(sum);
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