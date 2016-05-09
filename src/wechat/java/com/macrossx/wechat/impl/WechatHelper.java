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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.http.client.methods.HttpGet;

import com.macrossx.wechat.entity.WechatAccessToken;
import com.macrossx.wechat.http.WechatHttpClient;

import lombok.extern.java.Log;

@Log
public class WechatHelper implements IWechatHelper {
	@Inject
	@Named("macrossx.wechat.appid")
	private String appid;
	@Inject
	@Named("macrossx.wechat.appsecret")
	private String appsecret;

	private static WechatAccessToken _access_token;
	private static long _token_time = -1;

	/**
	 * 如果{@code _token_time}<当前时间则token超时，重新获取。实际情况下token有效时间为(7200-60)秒
	 */
	public Optional<WechatAccessToken> getAccessToken() {
		try {
			if (System.currentTimeMillis() < _token_time) {
				return Optional.of(_access_token);
			}
			long current = System.currentTimeMillis();
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(new URI(MessageFormat.format(WechatConstants.ACCESS_TOKEN_URL, appid, appsecret)));
			return new WechatHttpClient<WechatAccessToken>().send(httpGet, WechatAccessToken.class).map((e) -> {
				_access_token = e;
				_token_time = current + _access_token.getExpires_in() - 60;
				return _access_token;
			});
		} catch (URISyntaxException e) {
			log.info(e.getMessage());
			return Optional.empty();
		}

	}

}
