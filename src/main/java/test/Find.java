package test;

import java.io.FileInputStream;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import util.FileUtils;

public class Find {

	public static void main(String[] args) throws Exception {
		String dir = "E:/WEContent";
		List<String> fs = FileUtils.getAllFiles(dir,null);
		for (String f : fs) {
			if(!f.endsWith(".json")) {
				continue;
			}
			try (FileInputStream fis = new FileInputStream(f)){
				String json = new String(fis.readAllBytes());
				Gson gson = new Gson();
				JsonObject jo = gson.fromJson(json, JsonObject.class);
				String type = jo.get("type").getAsString();
				if(!"video".equals(type)) {
					System.out.println(type+"  ["+jo.get("title")+"]");
				}
			}
		}
	}

}
