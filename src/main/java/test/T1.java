package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Random;

import util.DbUtil;
import util.EncryptUtil;
import util.FileUtils;
import util.TimeUtil;

public class T1 {
	public static void main(String[] args) throws Exception {
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode("hSXhsRWaYkDGJ/OZZ1JeHA==");
		System.out.println(new String(decode));
		String wxid = "wx141bfb9b73c970a9";
		byte[] encs = EncryptUtil.getEncryptedPassword(wxid,"saltiest");
		System.out.println(new String(encs));
		FileInputStream fis = new FileInputStream("E:\\Download\\unweapp-0.1\\decode.wxapkg");
		byte[] bs = fis.readAllBytes();
		byte[] dcds = new byte[1024];
		System.arraycopy(bs, 0, dcds, 0, dcds.length);

		byte[] encrypt = EncryptUtil.aesEncrypt(dcds, encs, "the iv: 16 bytes");
		System.out.println(new String(encrypt));
		
		fis.close();
		FileOutputStream fos = new FileOutputStream("D:/temp/dfsfs.wxapkg");
		fos.write("V1MMWX".getBytes());
		fos.write(encrypt);
		fos.close();
		
		System.exit(1);
		
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(20);
		nf.setGroupingUsed(false);
		System.out.println(nf.format(333));
		int[] arr =new int[10];
		Random random = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Math.abs(random.nextInt(1000));
		}
		arr = new int[] {169, 249, 219, 228, 875, 79, 479, 774, 253, 810};
		
		System.out.println("a"+Arrays.toString(arr));
		int[] narr = new int[arr.length];
		System.arraycopy(arr, 0, narr, 0, arr.length);
		Arrays.sort(narr);
		System.out.println("b"+Arrays.toString(narr));
		Object lock = new Object();
		
//		synchronized (lock) {
			lock.wait();
//		}
		Thread.sleep(5000);
		
//		quickSort(arr,0,arr.length);
		System.out.println("s"+Arrays.toString(arr));
		System.out.println(Arrays.equals(arr, narr));
		// m2();
		m3("s", null);
		System.out.println();
		System.out.println(FileUtils.getAllFiles("D:/TTTtest", "*"));
//		System.exit(10086);
	}
	
	private static void bubbleSort(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length-1; j++) {
				if (arr[j] > arr[j + 1]) {
					int t = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = t;
				}
			}
		}
	}
	private static void quickSort(int[] arr, int off, int l) {
		if (l < 2) {
			return;
		}
		int lLen = l / 2;
		int pivot = arr[lLen];
		int[] narr = new int[l];
		System.arraycopy(arr, off, narr, 0, l);
		int lIndex = off, rIndex = off + l - 1;
		int max = off + l;
		for (int i = off, j = 0; i < max; i++, j++) {
			if (narr[j] < pivot) {
				arr[lIndex++] = narr[j];
			} else if (narr[j] > pivot) {
				arr[rIndex--] = narr[j];
			}
		}
		arr[lIndex] = pivot;
		quickSort(arr, off, lIndex);
		quickSort(arr, lIndex + 1, l - lIndex - 1);
	}
//39030 38960
//38177 38241
//2222
	private static void m3(String s,String a){
		System.out.println(s);
		String dff = "332";
		System.out.printf("%sss",dff);
	}
	private static void m2() throws Exception  {
		long t1 = System.currentTimeMillis();
		
		final String sql = "insert into test_table1 values (?,?,?,?,?,?)";
		Connection connection = DbUtil.connect("root", "123456");
//		connection.setAutoCommit(true);
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 1; i < 100000; i++) {
			statement.setInt(1, i);
			statement.setString(2, String.valueOf(i*2));
			statement.setTimestamp(3, Timestamp.valueOf(TimeUtil.toTimeStamp(LocalDateTime.now().plusDays(i%100))));
			statement.setString(4, String.valueOf(i*3));
			statement.setBigDecimal(5, BigDecimal.valueOf(i*4));
			statement.setString(6, String.valueOf(i*5).substring(0,1));
			statement.addBatch();
		}
		statement.executeBatch();
		connection.commit();
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}

	private static void m1() throws SQLException {
		//test
		Connection cnn = DbUtil.connect("root", "123456");
		DatabaseMetaData md = cnn.getMetaData();
		ResultSet res = md.getColumns(null, null, "test_table1", null);
		while (res.next()) {
			System.out.println(res.getString("COLUMN_NAME"));
			System.out.println(res.getString("REMARKS"));
		}
	}
}
