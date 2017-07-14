package com.example.viswaprathapn.stress_test;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by viswaprathap.n on 10-07-2017.
 */

public class CustomPhoneStateListener extends PhoneStateListener {

    Context context;
    public CustomPhoneStateListener(Context context){
        super();
        //this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber){
        super.onCallStateChanged(state, incomingNumber);
        Log.i(Constants.TAG,"State is"+ state);
    }
}
