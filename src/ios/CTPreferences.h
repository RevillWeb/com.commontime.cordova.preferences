//
//  CTPreferences.m
//  mDesign 10
//
//  Created by Gary Meehan on 17/03/2016.
//  Copyright (c) 2016 CommonTime Limited. All rights reserved.
//

#import <Cordova/CDV.h>

@interface CTPreferences : CDVPlugin

- (void) getAllPreferences: (CDVInvokedUrlCommand*) command;

@end