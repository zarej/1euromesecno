package rs.hakaton.euromesecno.sdk;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class SdkApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.showImageOnLoading(R.drawable.ic_launcher).build();
		// Create global configuration and initialize ImageLoader with this
		// configuration
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())

		.defaultDisplayImageOptions(defaultOptions)
//		.writeDebugLogs()
		.build();
		ImageLoader.getInstance().init(config);
	}
}
