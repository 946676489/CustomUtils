package util.format;

import lombok.Data;

@Data
public class TableData {
	private String columnName;
	private Type type;
	private String[] datas;

	public static TableData createData(String columnName,Type type, String...datas) {
		TableData td = new TableData();
		td.columnName = columnName;
		td.type = type;
		td.datas = datas;
		return td;
	}
	public static enum Type {
		TIMESTAMP,
		NUMERIC,
		STRING
	}
}
