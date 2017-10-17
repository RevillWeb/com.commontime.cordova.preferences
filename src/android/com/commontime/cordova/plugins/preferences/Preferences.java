package com.commontime.cordova.plugins.preferences;

import android.annotation.TargetApi;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Preferences extends CordovaPlugin {

	private static final String TAG = "Preferences";
	private static final String ACTION_GET_ALL_PREFERENCES = "getAllPreferences";
    private JSONObject preferencesJson;

    @Override
	protected void pluginInitialize() {
		preferencesJson = new JSONObject();

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

								if( prefName.equals("RELEASE_DEBUGGING") && prefValue.equals("true") ) {
									Log.i(TAG, "Release debugging enabled");
									setUpWebDebugging();
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
	}

	private void setUpWebDebugging() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WebView.setWebContentsDebuggingEnabled(true);
		}
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
