package com.reactnativeokadocmeet;

import android.util.Log;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReadableMap;
import com.reactnativeokadocmeet.constants.RNOkadocMeetFeatureFlagsConstants;
import com.reactnativeokadocmeet.constants.RNOkadocMeetOptionsConstants;
import com.reactnativeokadocmeet.constants.RNOkadocMeetUserInfoConstants;

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
    public void call(String url, ReadableMap userInfo, ReadableMap options, ReadableMap featureFlags) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            private RNOkadocMeetConferenceOptions.Builder getOptionsBuilder() {
                RNOkadocMeetUserInfo _userInfo = getUserInfo();
                RNOkadocMeetConferenceOptions.Builder optionsBuilder = new RNOkadocMeetConferenceOptions.Builder()
                        .setRoom(url)
                        .setSubject("Telemedicine")
                        .setUserInfo(_userInfo)
                        .setFeatureFlag("add-people.enabled", false)
                        .setFeatureFlag("calendar.enabled", false)
                        .setFeatureFlag("conference-timer.enabled", false)
                        .setFeatureFlag("chat.enabled", false)
                        .setFeatureFlag("invite.enabled", false)
                        .setFeatureFlag("kick-out.enabled", false)
                        .setFeatureFlag("live-streaming.enabled", false)
                        .setFeatureFlag("meeting-name.enabled", false)
                        .setFeatureFlag("pip.enabled", false)
                        .setFeatureFlag("raise-hand.enabled", false)
                        .setFeatureFlag("recording.enabled", false)
                        .setFeatureFlag("server-url-change.enabled", false)
                        .setFeatureFlag("video-share.enabled", false)
                        .setFeatureFlag("welcomepage.enabled", false);

                setOptionsFromOptions(optionsBuilder);
                setFlagsFromFeatureFlags(optionsBuilder);

                return optionsBuilder;
            }

            private RNOkadocMeetUserInfo getUserInfo() {
                RNOkadocMeetUserInfo _userInfo = new RNOkadocMeetUserInfo();

                if (userInfo != null) {
                    ReadableMapKeySetIterator iterator = userInfo.keySetIterator();
                    while (iterator.hasNextKey()) {
                        String key = iterator.nextKey();
                        ReadableType type = userInfo.getType(key);
                        switch (type) {
                            case String:
                                String value = userInfo.getString(key);

                                if (key.equals(RNOkadocMeetUserInfoConstants.DISPLAY_NAME)) {
                                    _userInfo.setDisplayName(value);
                                }

                                if (key.equals(RNOkadocMeetUserInfoConstants.EMAIL)) {
                                    _userInfo.setEmail(value);
                                }

                                if (key.equals(RNOkadocMeetUserInfoConstants.AVATAR_URL)) {
                                    try {
                                        _userInfo.setAvatar(new URL(value));
                                    } catch (MalformedURLException e) {
                                    }
                                }

                                break;
                            default:
                                break;
                        }
                    }
                }

                return _userInfo;
            }

            private void setOptionsFromOptions(RNOkadocMeetConferenceOptions.Builder optionsBuilder) {
                boolean audioOnly = false;
                boolean audioMuted = false;
                boolean videoMuted = false;

                if (options != null) {
                    ReadableMapKeySetIterator iterator = options.keySetIterator();
                    while (iterator.hasNextKey()) {
                        String key = iterator.nextKey();
                        ReadableType type = options.getType(key);
                        switch (type) {
                            case Boolean:
                                boolean value = options.getBoolean(key);

                                if (key.equals(RNOkadocMeetOptionsConstants.AUDIO_ONLY)) {
                                    audioOnly = value;
                                }

                                if (key.equals(RNOkadocMeetOptionsConstants.AUDIO_MUTED)) {
                                    audioMuted = value;
                                }

                                if (key.equals(RNOkadocMeetOptionsConstants.VIDEO_MUTED)) {
                                    videoMuted = value;
                                }

                                break;
                            default:
                                break;
                        }
                    }
                }

                optionsBuilder
                        .setAudioOnly(audioOnly)
                        .setAudioMuted(audioMuted)
                        .setVideoMuted(videoMuted);
            }

            private void setFlagsFromFeatureFlags(RNOkadocMeetConferenceOptions.Builder optionsBuilder) {
                if (featureFlags != null) {
                    ReadableMapKeySetIterator iterator = featureFlags.keySetIterator();
                    while (iterator.hasNextKey()) {
                        String key = iterator.nextKey();
                        ReadableType type = featureFlags.getType(key);
                        switch (type) {
                            case Boolean:
                                boolean value = featureFlags.getBoolean(key);

                                if (key.equals(RNOkadocMeetFeatureFlagsConstants.FILMSTRIP_ENABLED)) {
                                    optionsBuilder.setFeatureFlag("filmstrip.enabled", value);
                                }

                                if (key.equals(RNOkadocMeetFeatureFlagsConstants.TILE_VIEW_ENABLED)) {
                                    optionsBuilder.setFeatureFlag("tile-view.enabled", value);
                                }

                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            @Override
            public void run() {
                if (mOkadocMeetViewReference.getOkadocMeetView() != null) {
                    RNOkadocMeetConferenceOptions okadocMeetConferenceOptions = getOptionsBuilder().build();

                    try {
                        mOkadocMeetViewReference.getOkadocMeetView().leave();
                    } catch (Exception e) {}

                    mOkadocMeetViewReference.getOkadocMeetView().join(okadocMeetConferenceOptions);
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
