package com.commontime.cordova.plugins.preferences;

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

public class Preferences extends CordovaPlugin {

	private static final String TAG = "Preferences";
	private static final String ACTION_GET_ALL_PREFERENCES = "getAllPreferences";
    private JSONObject preferencesJson;

	public class Filewalker {

		public void walk(File root) {

			File[] list = root.listFiles();

			for (File f : list) {
				if (f.isDirectory()) {
					Log.d("FILEWALKER", "Dir: " + f.getAbsoluteFile());
					walk(f);
				}
				else {
					Log.d("FILEWALKER", "File: " + f.getAbsoluteFile());
				}
			}
		}
	}

    @Override
	protected void pluginInitialize() {
		preferencesJson = new JSONObject();

		cordova.getThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				Filewalker fw = new Filewalker();
				fw.walk(cordova.getActivity().getFilesDir());
			}
		});


		final int identifier = cordova.getActivity().getResources().getIdentifier("config",
				"xml", cordova.getActivity().getPackageName());
		final XmlResourceParser parser = cordova.getActivity().getResources().getXml(identifier);

		try {
			for (int eventType = -1; eventType != XmlResourceParser.END_DOCUMENT; eventType = parser.next()) {

                if (eventType == XmlResourceParser.START_TAG) {
                    String s = parser.getName();

					String prefName = null;
					String prefValue = null;

                    if (s.equals("preference")) {
                        for (int attr = 0; attr < parser.getAttributeCount(); attr++) {
                            String name = parser.getAttributeName(attr);
                            String val = parser.getAttributeValue(attr);
                            System.out.println();

							if( name.equals( "name") ) {
								prefName = val;
							} else if( name.equals( "value") ) {
								prefValue = val;
							}

							if( prefName != null && prefValue != null ) {
								try {
									preferencesJson.put(prefName, prefValue);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								break;
							}
                        }
                    }
                }
            }
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		parser.close();
	}

	@Override
	public void onDestroy() {
	}

	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		if (action.equals(ACTION_GET_ALL_PREFERENCES)) {
			callbackContext.success(preferencesJson);
			return true;
		}

		return false;
	}
}
