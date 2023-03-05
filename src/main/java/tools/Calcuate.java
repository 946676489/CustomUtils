package tools;

import java.math.BigDecimal;
import java.util.List;

import util.FileUtils;

public class Calcuate {

	public static void main(String[] args) {
		// RMB:-4436.19
		// USD:552.78
//		List<String> lines = FileUtils.readLines("./src/main/resources/conf/rmb.txt");
		List<String> lines = FileUtils.readLines("./src/main/resources/conf/usd.txt");
		BigDecimal decimal = BigDecimal.ZERO;
		for (String line : lines) {
			if(line.charAt(0)=='#') {
				continue;
			}
			double val = Double.parseDouble(line);
			decimal = decimal.add(BigDecimal.valueOf(val));
		}
		
//		System.out.println(decimal);
		System.out.println(decimal.multiply(BigDecimal.valueOf(6.3499)));
		    

	}

}
