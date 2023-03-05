package wxapp;

public class WxapkgItem {
	private String name;
	private int start;
	private int length;

	public WxapkgItem(String name, int start, int length) {
		this.name = name;
		this.start = start;
		this.length = length;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String toString() {
		return "WxapkgItem{name='" + this.name + '\'' + ", start=" + this.start + ", length=" + this.length + '}';
	}
}