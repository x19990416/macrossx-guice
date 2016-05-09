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

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.macrossx.wechat.entity.WecahtMessageTemplateRespObj;
import com.macrossx.wechat.entity.WechatAccessToken;
import com.macrossx.wechat.entity.WechatMessageTemplate;
import com.macrossx.wechat.http.WechatHttpClient;

import lombok.extern.java.Log;

@Log
public class WechatMessageHelper implements IWechatMessageHelper {
	@Inject
	private IWechatHelper wechatHelper;

	@Override
	public Optional<WecahtMessageTemplateRespObj> sendTemplate(WechatMessageTemplate template) {
		try {
			Optional<WechatAccessToken> token = wechatHelper.getAccessToken();
			if (token.isPresent()) {
				WechatAccessToken accessToken = token.get();
				HttpPost httpPost = new HttpPost();
				System.out.println(new Gson().toJson(template));
				httpPost.setEntity(new StringEntity(new Gson().toJson(template), "utf-8"));
				httpPost.setURI(new URI(MessageFormat.format(WechatConstants.MESSAGE_TEMPLATE_SEND_URL,
						accessToken.getAccess_token())));
				return new WechatHttpClient<WecahtMessageTemplateRespObj>().send(httpPost,
						WecahtMessageTemplateRespObj.class);
			}
		} catch (URISyntaxException e) {
			log.info(e.getMessage());
		}
		return Optional.empty();
	}

}
