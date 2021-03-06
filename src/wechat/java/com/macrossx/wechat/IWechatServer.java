/**
 * Copyright (C) 2016 X-Forever.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.macrossx.wechat;

import com.macrossx.wechat.entity.server.WechatHttpEntity;
import com.macrossx.wechat.entity.server.WechatImageRequest;
import com.macrossx.wechat.entity.server.WechatLinkRequest;
import com.macrossx.wechat.entity.server.WechatLocationEventRequest;
import com.macrossx.wechat.entity.server.WechatLocationRequest;
import com.macrossx.wechat.entity.server.WechatMenuEventRequest;
import com.macrossx.wechat.entity.server.WechatQrsceneEventRequest;
import com.macrossx.wechat.entity.server.WechatSubscribeEventRequest;
import com.macrossx.wechat.entity.server.WechatTextRequest;
import com.macrossx.wechat.entity.server.WechatTextResponse;
import com.macrossx.wechat.entity.server.WechatVideoRequest;
import com.macrossx.wechat.entity.server.WechatVoiceRequest;

public interface IWechatServer {
	default public WechatHttpEntity onTextMessage(WechatTextRequest request) {
		System.out.println("onTextMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onImageMessage(WechatImageRequest request) {
		System.out.println("onImageMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onVoiceMessage(WechatVoiceRequest request) {
		System.out.println("onVoiceMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	
	default public WechatHttpEntity onVideoMessage(WechatVideoRequest request) {
		System.out.println("onVideoMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onShortVideoMessage(WechatVideoRequest request) {
		System.out.println("onShortVideoMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onLocationMessage(WechatLocationRequest request) {
		System.out.println("onPositionMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onLinkMessage(WechatLinkRequest request) {
		System.out.println("onLinkMessage[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent("aaaapx");
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	
	default public WechatHttpEntity onEventSubscribe(WechatSubscribeEventRequest request){
		System.out.println("onEventSubscribe[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent(request.getFromUserName());
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	
	default public WechatHttpEntity onEventUnsubscribe(WechatSubscribeEventRequest request){
		System.out.println("onEventUnsubscribe[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent(request.getFromUserName());
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	
	default public WechatHttpEntity onEventQrscene(WechatQrsceneEventRequest request){
		System.out.println("onEventQrscene[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent(request.getFromUserName());
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onEventLocation(WechatLocationEventRequest request){
		System.out.println("onEventLocation[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent(request.getFromUserName()+":"+request.getLocation_X()+":"+request.getLocation_Y()+":"+request.getPrecision());
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
	default public WechatHttpEntity onEventMenu(WechatMenuEventRequest request){
		System.out.println("onEventMenu[" + request + "]");
		WechatTextResponse ret =	new WechatTextResponse();
		ret.setContent(request.getFromUserName()+":"+request.getEvent()+":"+request.getEventKey());
		ret.setCreateTime(System.currentTimeMillis());
		ret.setToUserName(request.getFromUserName());
		ret.setFromUserName(request.getToUserName());
		return ret;
	}
}
