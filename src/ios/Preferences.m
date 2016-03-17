//
//  Preferences.m
//  mDesign 10
//
//  Created by Gary Meehan on 17/03/2016.
//  Copyright (c) 2016 CommonTime Limited. All rights reserved.
//

#import "Preferences.h"

@implementation Preferences

- (void) getAllPreferences: (CDVInvokedUrlCommand*) command
{
  NSDictionary* preferences = nil;
  
  if ([self.viewController isKindOfClass: [CDVViewController class]])
  {
    preferences = ((CDVViewController*) self.viewController).settings;
  }
  
  CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary: preferences];
  
  [self.commandDelegate sendPluginResult:result callbackId: command.callbackId];
}

@end