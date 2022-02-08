/**
 * @providesModule OkadocMeet
 */

import {NativeModules, requireNativeComponent} from 'react-native';

export const OkadocMeetView = requireNativeComponent('RNOkadocMeetView');
export const OkadocMeetModule = NativeModules.RNOkadocMeetView;
const call = OkadocMeetModule.call;
const audioCall = OkadocMeetModule.audioCall;
OkadocMeetModule.call = (url, userInfo) => {
  userInfo = userInfo || {};
  call(url, userInfo);
};
OkadocMeetModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
};
export default OkadocMeetModule;
