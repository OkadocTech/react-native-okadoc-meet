#import "RNOkadocMeetViewManager.h"
#import "RNOkadocMeetView.h"
#import <JitsiMeetSDK/JitsiMeetUserInfo.h>
#import <JitsiMeetSDK/JitsiMeetConferenceOptions.h>

@implementation RNOkadocMeetViewManager{
    RNOkadocMeetView *okadocMeetView;
}

RCT_EXPORT_MODULE(RNOkadocMeetView)
RCT_EXPORT_VIEW_PROPERTY(onConferenceJoined, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onConferenceTerminated, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onConferenceWillJoin, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onEnteredPip, RCTBubblingEventBlock)

- (UIView *)view
{
  okadocMeetView = [[RNOkadocMeetView alloc] init];
  okadocMeetView.delegate = self;
  return okadocMeetView;
}

RCT_EXPORT_METHOD(initialize)
{
    RCTLogInfo(@"Initialize is deprecated in v2");
}

RCT_EXPORT_METHOD(call:(NSString *)urlString userInfo:(NSDictionary *)userInfo)
{
    RCTLogInfo(@"Load URL %@", urlString);
    JitsiMeetUserInfo * _userInfo = [[JitsiMeetUserInfo alloc] init];
    if (userInfo != NULL) {
      if (userInfo[@"displayName"] != NULL) {
        _userInfo.displayName = userInfo[@"displayName"];
      }
      if (userInfo[@"email"] != NULL) {
        _userInfo.email = userInfo[@"email"];
      }
      if (userInfo[@"avatar"] != NULL) {
        NSURL *url = [NSURL URLWithString:[userInfo[@"avatar"] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
      }
    }
    dispatch_sync(dispatch_get_main_queue(), ^{
        JitsiMeetConferenceOptions *options = [JitsiMeetConferenceOptions fromBuilder:^(JitsiMeetConferenceOptionsBuilder *builder) {
            builder.room = urlString;
            builder.userInfo = _userInfo;
            [builder setFeatureFlag:@"add-people.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"chat.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"conference-timer.enabled" withBoolean:YES];
            [builder setFeatureFlag:@"help.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"invite.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"kick-out.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"live-streaming.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"lobby-mode.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"meeting-password.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"pip.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"raise-hand.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"security-options.enabled" withBoolean:NO];
            [builder setFeatureFlag:@"video-share.enabled" withBoolean:NO];
        }];
        [okadocMeetView join:options];
    });
}

RCT_EXPORT_METHOD(audioCall:(NSString *)urlString userInfo:(NSDictionary *)userInfo)
{
    RCTLogInfo(@"Load Audio only URL %@", urlString);
    JitsiMeetUserInfo * _userInfo = [[JitsiMeetUserInfo alloc] init];
    if (userInfo != NULL) {
      if (userInfo[@"displayName"] != NULL) {
        _userInfo.displayName = userInfo[@"displayName"];
      }
      if (userInfo[@"email"] != NULL) {
        _userInfo.email = userInfo[@"email"];
      }
      if (userInfo[@"avatar"] != NULL) {
        NSURL *url = [NSURL URLWithString:[userInfo[@"avatar"] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
      }
    }
    dispatch_sync(dispatch_get_main_queue(), ^{
        JitsiMeetConferenceOptions *options = [JitsiMeetConferenceOptions fromBuilder:^(JitsiMeetConferenceOptionsBuilder *builder) {
            builder.room = urlString;
            builder.userInfo = _userInfo;
            builder.subject = @"Telemedicine";
            builder.audioOnly = YES;
        }];
        [okadocMeetView leave];
        [okadocMeetView join:options];
    });
}

RCT_EXPORT_METHOD(endCall)
{
    dispatch_sync(dispatch_get_main_queue(), ^{
        [okadocMeetView leave];
    });
}

#pragma mark OkadocMeetViewDelegate

- (void)conferenceJoined:(NSDictionary *)data {
    RCTLogInfo(@"Conference joined");
    if (!okadocMeetView.onConferenceJoined) {
        return;
    }
    okadocMeetView.onConferenceJoined(data);
}

- (void)conferenceTerminated:(NSDictionary *)data {
    RCTLogInfo(@"Conference terminated");
    if (!okadocMeetView.onConferenceTerminated) {
        return;
    }
    okadocMeetView.onConferenceTerminated(data);
}

- (void)conferenceWillJoin:(NSDictionary *)data {
    RCTLogInfo(@"Conference will join");
    if (!okadocMeetView.onConferenceWillJoin) {
        return;
    }

    okadocMeetView.onConferenceWillJoin(data);
}

- (void)enterPictureInPicture:(NSDictionary *)data {
    RCTLogInfo(@"Enter Picture in Picture");
    if (!okadocMeetView.onEnteredPip) {
        return;
    }

    okadocMeetView.onEnteredPip(data);
}

@end
