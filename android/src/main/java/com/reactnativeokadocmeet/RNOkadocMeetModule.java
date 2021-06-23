package com.reactnativeokadocmeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;

@ReactModule(name = RNOkadocMeetModule.MODULE_NAME)
public class RNOkadocMeetModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNOkadocMeetModule";
    private IRNOkadocMeetViewReference mOkadocMeetViewReference;

    public RNOkadocMeetModule(ReactApplicationContext reactContext, IRNOkadocMeetViewReference okadocMeetViewReference) {
        super(reactContext);
        mOkadocMeetViewReference = okadocMeetViewReference;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Log.d("OkadocMeet", "Initialize is deprecated in v2");
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOkadocMeetViewReference.getOkadocMeetView() != null) {
                    RNOkadocMeetUserInfo _userInfo = new RNOkadocMeetUserInfo();
                    if (userInfo != null) {
                        if (mOkadocMeetViewReference.getOkadocMeetView() == null) {
                            call(url, userInfo);
                        }
                        else {
                            if (userInfo.hasKey("displayName")) {
                                _userInfo.setDisplayName(userInfo.getString("displayName"));
                            }
                            if (userInfo.hasKey("email")) {
                                _userInfo.setEmail(userInfo.getString("email"));
                            }
                            if (userInfo.hasKey("avatar")) {
                                String avatarURL = userInfo.getString("avatar");
                                try {
                                    _userInfo.setAvatar(new URL(avatarURL));
                                }
                                catch (MalformedURLException e) {
                                }
                            }
                        }
                    }
                    RNOkadocMeetConferenceOptions options = new RNOkadocMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioMuted(false)
                            .setVideoMuted(false)
                            .setAudioOnly(false)
                            .setWelcomePageEnabled(false)
                            .setSubject("Telemedicine")
                            .setUserInfo(_userInfo)
                            .setFeatureFlag("add-people.enabled", false)
                            .setFeatureFlag("chat.enabled", false)
                            .setFeatureFlag("conference-timer.enabled", false)
                            .setFeatureFlag("invite.enabled", false)
                            .setFeatureFlag("pip.enabled", false)
                            .build();
                    mOkadocMeetViewReference.getOkadocMeetView().leave();
                    mOkadocMeetViewReference.getOkadocMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOkadocMeetViewReference.getOkadocMeetView() != null) {
                    RNOkadocMeetUserInfo _userInfo = new RNOkadocMeetUserInfo();
                    if (userInfo != null) {
                        if (mOkadocMeetViewReference.getOkadocMeetView() == null) {
                            audioCall(url, userInfo);
                        }
                        else {
                            if (userInfo.hasKey("displayName")) {
                                _userInfo.setDisplayName(userInfo.getString("displayName"));
                            }
                            if (userInfo.hasKey("email")) {
                                _userInfo.setEmail(userInfo.getString("email"));
                            }
                            if (userInfo.hasKey("avatar")) {
                                String avatarURL = userInfo.getString("avatar");
                                try {
                                    _userInfo.setAvatar(new URL(avatarURL));
                                } 
                                catch (MalformedURLException e) {
                                }
                            }
                        }
                    }
                    RNOkadocMeetConferenceOptions options = new RNOkadocMeetConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mOkadocMeetViewReference.getOkadocMeetView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mOkadocMeetViewReference.getOkadocMeetView() != null) {
                    mOkadocMeetViewReference.getOkadocMeetView().leave();
                }
            }
        });
    }
}
