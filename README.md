# react-native-okadoc-meet

React native wrapper for Okadoc Meet SDK

## Install

follow the guidance in here:
https://okadoc.atlassian.net/wiki/spaces/TECH/pages/304120147/Okadoc+Package+registry.

## Use

The following component is an example of use:

```
import React, { useEffect } from 'react';
import { StatusBar, View } from 'react-native';
import OkadocMeet, { OkadocMeetView } from 'react-native-okadoc-meet';

const VideoCall = (props) => {
  const {
    navigation,
    route: {
      params: {meetUrl, email, avatar, displayName},
    }
  } = props;

  useEffect(() => {
    StatusBar.setHidden(false, 'none'); // don't remove this
    StatusBar.setTranslucent(false); // don't remove this.
    StatusBar.setBackgroundColor('#000000'); // you can remove
    StatusBar.setBarStyle('light-content'); // you can remove

    const userInfo = {displayName, email, avatar};
    console.log('url', meetUrl);
    console.log('userInfo', userInfo);

    OkadocMeet.call(meetUrl, userInfo);
    /* You can also use OkadocMeet.audioCall(url) for audio only call */
    /* You can programmatically end the call with OkadocMeet.endCall() */
  }, []);

  useEffect(() => {
    return () => {
      OkadocMeet.endCall();
    };
  });

  const onConferenceJoined = (nativeEvent) => {
    /* Conference joined event */
  }

  const onConferenceWillJoin = (nativeEvent) => {
    /* Conference will join event */
  }

  const onConferenceTerminated = (nativeEvent) => {
    navigation.pop();
  }

  return (
    <View style={{backgroundColor: 'black', flex: 1}}>
      <OkadocMeetView
        onConferenceJoined={onConferenceJoined}
        onConferenceTerminated={onConferenceTerminated}
        onConferenceWillJoin={onConferenceWillJoin}
        style={{flex: 1, height: '100%', width: '100%'}}
      />
    </View>
  );
};

export default VideoCall;
```

### Side-note

If your app already includes `react-native-locale-detector` or `react-native-vector-icons`, you must exclude them from the `react-native-okadoc-meet` project implementation with the following code (even if you're app uses autolinking with RN > 0.60):

```
  implementation(project(':react-native-okadoc-meet')) {
    exclude group: 'com.facebook.react',module:'react-native-locale-detector'
    exclude group: 'com.facebook.react',module:'react-native-vector-icons'
    // Un-comment below if using hermes
    //exclude group: 'com.facebook',module:'hermes'
    // Un-comment any packages below that you have added to your project to prevent `duplicate_classes` errors
    //exclude group: 'com.facebook.react',module:'react-native-async-storage'
    //exclude group: 'com.facebook.react',module:'react-native-community-async-storage'
    //exclude group: 'com.facebook.react',module:'react-native-community_netinfo'
    //exclude group: 'com.facebook.react',module:'react-native-fetch-blob'
    //exclude group: 'com.facebook.react',module:'react-native-linear-gradient'
    //exclude group: 'com.facebook.react',module:'react-native-sound'
    //exclude group: 'com.facebook.react',module:'react-native-splash-screen'
    //exclude group: 'com.facebook.react',module:'react-native-svg'
    //exclude group: 'com.facebook.react',module:'react-native-webview'
  }
```
