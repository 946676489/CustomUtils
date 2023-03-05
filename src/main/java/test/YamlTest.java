package test;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	public static void main(String[] args) throws Exception {
		Yaml yaml = new Yaml();
		String yml = "src/main/java/test/test.yml";
		
		YAMLBean bean = new YAMLBean();
		bean.setAs(new String[]{"xx","aa"});
		String dump = yaml.dump(bean);
		System.out.println(dump);
		
		bean = yaml.loadAs(dump, YAMLBean.class);
		System.out.println(Arrays.toString(bean.getAs()));
		
		Object object = yaml.load(new FileInputStream(yml));
		System.out.println(((Map)object).get("qq"));

	}
	public static class YAMLBean {
		private String a;
		private String b;
		private String c;
		private String[] as;
		private String[] bs;
		public String getA() {
			return a;
		}
		public void setA(String a) {
			this.a = a;
		}
		public String getB() {
			return b;
		}
		public void setB(String b) {
			this.b = b;
		}
		public String getC() {
			return c;
		}
		public void setC(String c) {
			this.c = c;
		}
		public String[] getAs() {
			return as;
		}
		public void setAs(String[] as) {
			this.as = as;
		}
		public String[] getBs() {
			return bs;
		}
		public void setBs(String[] bs) {
			this.bs = bs;
		}
	}
}
