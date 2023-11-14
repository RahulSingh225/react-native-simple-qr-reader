package com.simpleqrreader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.gms.vision.barcode.Barcode;

@ReactModule(name = SimpleQrReaderModule.NAME)
public class SimpleQrReaderModule extends ReactContextBaseJavaModule {
  public static final String NAME = "SimpleQrReader";
private Promise mPromise;
  private static ReactApplicationContext reactContext;
  private static int barCodeActivityRequest = 1208;
   private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      if (resultCode != Activity.RESULT_OK) mPromise.reject("Error","kk");
      if (requestCode == barCodeActivityRequest && data != null) {
        Barcode barcode =
          data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
        Log.d("TATA",barcode.rawValue);
        if (!barcode.rawValue.isEmpty()) {

          String code = barcode.rawValue;
//                if (isRetailerCoupon!!) {
//                    etBarcode.setText(code)
//                    sendBarcode()
//                } else {
//                    if (code?.length!! > 19) {
//                        val substringCode = barcode.rawValue.substring(9)
//                        etBarcode.setText(substringCode)
//                    } else {
//                        etBarcode.setText(code)
//                    }
//                }
          mPromise.resolve(code);

        } else {
          mPromise.resolve("Invalid Bar Code");
        }
      }
      super.onActivityResult(requestCode, resultCode, data);
    }
   };

  public SimpleQrReaderModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void scanQR(Promise promise) {
    mPromise = promise;
    Activity currentActivity = getCurrentActivity();
    Intent launchIntent = new Intent(this.reactContext,BarcodeReaderActivity.class);
    currentActivity.startActivityForResult(launchIntent, barCodeActivityRequest);
  }
}
