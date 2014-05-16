package rs.hakaton.euromesecno.sdk.model;

import java.util.HashMap;
import java.util.Map;

public class Settings {
	String id;
	int ui_version;
	String ui_android;
	String starting_image;
	Map<String, String> i18n = new HashMap<String, String>();
	
	public String getId() {
		return id;
	}
	public int getUi_version() {
		return ui_version;
	}
	public String getUi_android() {
		return ui_android;
	}
	public String getStarting_image() {
		return starting_image;
	}
	public Map<String, String> getI18n() {
		return i18n;
	}
	
	
}
