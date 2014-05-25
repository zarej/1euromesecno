package rs.hakaton.euromesecno.sdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefernceHelper {

	private static final String ICON_CREATED = "icon_created";
	private static final String ALARM_SET_FIRST_TIME = "alarm_first_time_created";
	
	
	public static void setIconCreated(Context c, boolean isCreated) {
		setBoolPreference(c, ICON_CREATED, isCreated);
	}

	public static boolean isIconCreated(Context c) {
		return getBoolPreference(c, ICON_CREATED);
	}
	
	public static void setAlarmFirstTimeStarted(Context c, boolean isStarted) {
		setBoolPreference(c, ALARM_SET_FIRST_TIME, isStarted);
	}

	public static boolean isAlarmFirstTimeStarted(Context c) {
		return getBoolPreference(c, ALARM_SET_FIRST_TIME);
	}
	
	public static boolean compareAndUpdateStringPreference(Context c, String key, String value) {
		if (value.equals(getStringPreference(c, key))) {
			return false;
		} else {
			setStringPreference(c, key, value);
			return true;
		}
	}
	
	public static void setBoolPreference(Context c, String key, boolean value) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
	    editor.putBoolean(key, value);
	    editor.commit();
	}
	
	public static boolean getBoolPreference(Context c, String key) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
	    return pref.getBoolean(key, false);
	}
	
	public static void setIntPreference(Context c, String key, int value) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
	    editor.putInt(key, value);
	    editor.commit();
	}
	
	public static int getIntPreference(Context c, String key) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
	    return pref.getInt(key, 0);
	}
	
	public static void setStringPreference(Context c, String key, String value) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
		SharedPreferences.Editor editor = pref.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	public static String getStringPreference(Context c, String key) {
		SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
	    return pref.getString(key, null);
	}
	
}
