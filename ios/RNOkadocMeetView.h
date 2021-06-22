#import <JitsiMeetSDK/JitsiMeet.h>
#import <JitsiMeetSDK/JitsiMeetView.h>

#import <React/RCTComponent.h>

@interface RNOkadocMeetView : JitsiMeetView
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceJoined;
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceTerminated;
@property (nonatomic, copy) RCTBubblingEventBlock onConferenceWillJoin;
@property (nonatomic, copy) RCTBubblingEventBlock onEnteredPip;
@end
