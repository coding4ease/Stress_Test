package com.example.viswaprathapn.stress_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * Created by viswaprathap.n on 10-07-2017.
 */

public abstract class PhoneStateBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Log.i(Constants.TAG, getClass().getSimpleName());
        telephonyManager.listen(new CustomPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
    }
}
