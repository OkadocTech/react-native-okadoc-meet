package com.reactnativeokadocmeet;

import android.os.Bundle;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.module.annotations.ReactModule;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

import static java.security.AccessController.getContext;

@ReactModule(name = RNOkadocMeetViewManager.REACT_CLASS)
public class RNOkadocMeetViewManager extends SimpleViewManager<RNOkadocMeetView> implements JitsiMeetViewListener {
    public static final String REACT_CLASS = "RNOkadocMeetView";
    private IRNOkadocMeetViewReference mOkadocMeetViewReference;
    private ReactApplicationContext mReactContext;

    public RNOkadocMeetViewManager(ReactApplicationContext reactContext, IRNOkadocMeetViewReference okadocMeetViewReference) {
        mOkadocMeetViewReference = okadocMeetViewReference;
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNOkadocMeetView createViewInstance(ThemedReactContext context) {
        if (mOkadocMeetViewReference.getOkadocMeetView() == null) {
            RNOkadocMeetView view = new RNOkadocMeetView(context.getCurrentActivity());
            view.setListener(this);
            mOkadocMeetViewReference.setOkadocMeetView(view);
        }
        return mOkadocMeetViewReference.getOkadocMeetView();
    }

    public void onConferenceJoined(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mOkadocMeetViewReference.getOkadocMeetView().getId(),
                "conferenceJoined",
                event);
    }

    public void onConferenceTerminated(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        event.putString("error", (String) data.get("error"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mOkadocMeetViewReference.getOkadocMeetView().getId(),
                "conferenceTerminated",
                event);
    }

    public void onConferenceWillJoin(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mOkadocMeetViewReference.getOkadocMeetView().getId(),
                "conferenceWillJoin",
                event);
    }

    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("conferenceJoined", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceJoined")))
                .put("conferenceTerminated", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceTerminated")))
                .put("conferenceWillJoin", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceWillJoin")))
                .build();
    }
}