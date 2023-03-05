package util.format;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

public class DataFormatter {
	public static final String DB_NULL_VALUE = "«&#32;NULL&#32;»";
	
	private static final String TABLE = "<table border=\"1\"%s>";
	private static final String HEAD = "<th style=\"%s\">%s</th>";
	private static final String CONTENT = "<td style=\"%s\" nowrap>%s</td>";
	private static final String STYLE_FONT = "font-family: '%s'";
	private static final String STYLE_COMMON_FORMATE = "mso-number-format: '\\@'";
	private static final String STYLE_NUM_FORMATE = "mso-number-format:0";
	private static final String STYLE_DATE_FORMATE = "mso-number-format:'\\yyyy/M/d h:mm:ss'";
	private static final String STYLE_ALIGN_RIGHT = "text-align: right";
	private static final String STYLE_ALIGN_LEFT = "text-align: left";
	private static final String STYLE_COLOR = "color: #%s";
	private static final String STYLE_BACKGROUND_COLOR = "background-color: #%s";
	private static final String NULL_VALUE_COLOR = "7F878F";
	private static final String AUTO_COLOR = "000000";
	private DataFormatter() {
	}
	
	public static String formate2Excel(TableData[] datas) {
		Map<String, TableData> map = new LinkedHashMap<>();
		int datCnt = 0;
		for (TableData data : datas) {
			map.put(data.getColumnName(), data);
			if(datCnt!=0&&datCnt!=data.getDatas().length) {
				throw new RuntimeException("The number of datas is inconsistent");
			}
			datCnt = data.getDatas().length;
		}
		StringBuilder sb = new StringBuilder(String.format(TABLE," style=\"font-size: 11pt;\""));
		sb.append(System.lineSeparator());
		sb.append("<tr>");
		sb.append(System.lineSeparator());
		
		StringJoiner headers = new StringJoiner(System.lineSeparator());
		StringJoiner[] values = new StringJoiner[datCnt];
		for (int i = 0; i < values.length; i++) {
			values[i] = new StringJoiner(System.lineSeparator(),"<tr>"+System.lineSeparator(),System.lineSeparator()+"</tr>");
		}
		
		for (Entry<String, TableData> e : map.entrySet()) {
			StringJoiner hstyle = new StringJoiner("; ");
			hstyle.add(STYLE_COMMON_FORMATE)
			.add(STYLE_ALIGN_LEFT)
			.add(String.format(STYLE_COLOR,AUTO_COLOR))
			.add(String.format(STYLE_BACKGROUND_COLOR,"87E7AD"));
			headers.add(String.format(HEAD, hstyle.toString(), e.getKey()));
			
			for (int i = 0; i < e.getValue().getDatas().length; i++) {
				String data = e.getValue().getDatas()[i];
				StringJoiner vstyle = new StringJoiner("; ");
				if(data==null) {
					vstyle.add(String.format(STYLE_COLOR,NULL_VALUE_COLOR));
					values[i].add(String.format(CONTENT, vstyle.toString(), DB_NULL_VALUE));
					continue;
				}
				switch (e.getValue().getType()) {
				case TIMESTAMP:
					vstyle.add(STYLE_DATE_FORMATE)
					.add(STYLE_ALIGN_RIGHT);
					break;
				case NUMERIC:
					vstyle.add(STYLE_NUM_FORMATE)
					.add(STYLE_ALIGN_RIGHT);
					break;
				default:
					vstyle.add(STYLE_COMMON_FORMATE);
					break;
				}
				values[i].add(String.format(CONTENT, vstyle.toString(), data));
			}
			
		}
		sb.append(headers)
		.append(System.lineSeparator())
		.append("</tr>")
		.append(System.lineSeparator());
		for (StringJoiner v: values) {
			sb.append(v)
			.append(System.lineSeparator());
		}
		sb.append("</table>");
		return sb.toString();
	}
}
