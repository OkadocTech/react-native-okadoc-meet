/**
 * @providesModule OkadocMeet
 */

import {NativeModules, requireNativeComponent} from 'react-native';

export const OkadocMeetView = requireNativeComponent('RNOkadocMeetView');
export const OkadocMeetModule = NativeModules.RNOkadocMeetModule;
const call = OkadocMeetModule.call;
const audioCall = OkadocMeetModule.audioCall;
const endCall = OkadocMeetModule.endCall;
OkadocMeetModule.call = (url, userInfo, meetOptions, meetFeatureFlags) => {
  userInfo = userInfo ?? {};
  meetOptions = meetOptions ?? {};
  meetFeatureFlags = meetFeatureFlags ?? {};
  call(url, userInfo, meetOptions, meetFeatureFlags);
};
OkadocMeetModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
};
OkadocMeetModule.endCall = () => {
  endCall();
};
export default OkadocMeetModule;
