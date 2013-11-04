package edu.cmu.intentsniffer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LoggingReceiver extends BroadcastReceiver {

	protected String mName = "Unamed";
	protected IntentSnifferMainActivity mSniffer;

	/**
	 * @param name
	 *            of this logging receiver, might give hints to the IntentFilter
	 *            used to reach it etc.
	 */
	public LoggingReceiver(String name, IntentSnifferMainActivity is) {
		super();
		this.mName = name;
		this.mSniffer = is;
	}

	public void onReceive(Context c, Intent i) {
		try {
			mSniffer.receiveIntent(this.mName, i);
		} catch (Exception e) {
			Log.e("LOGGINGRECEIVER", mName + " error logging intent: "
					+ e.getMessage());
		}
	}
}
