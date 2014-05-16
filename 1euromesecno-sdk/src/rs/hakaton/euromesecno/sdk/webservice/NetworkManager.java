package rs.hakaton.euromesecno.sdk.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * Singleton NetworkManager
 */
public class NetworkManager {
	/**
	 * Allowed network timeout.
	 */
	public static final int TIMEOUT = 30000;
	private static ConnectivityManager connect;
	private static NetworkInfo wifi;
	private static NetworkInfo mobile;
	private static NetworkManager instance;

	/**
	 * Creates instance of the manager
	 * 
	 * @param context
	 * 
	 * @return instance returns the instance of the singleton class
	 */
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	private NetworkManager() {
		super();
	}
	
	public boolean isInRoaming(Context context) {
		connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect != null) {
			wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			mobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (wifi!=null && wifi.isConnected()) {
				return false;
			} else if (mobile!=null && mobile.isConnected()) {
				if (mobile.isRoaming()) {
					return true;
				}
				// else{
				// return true; TODO:roaming define
				// }
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * isInternetOn method can be extend for advance mobile/roaming settings
	 * 
	 * @return if Internet is on
	 */
	public boolean isInternetOn(Context context) {
		connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connect != null) {
			wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			mobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (wifi!=null && wifi.isConnected()) {
				return true;
			} else if (mobile!=null && mobile.isConnected()) {
				if (mobile.isRoaming()) {
					return false;
				}
				// else{
				// return true; TODO:roaming define
				// }
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
