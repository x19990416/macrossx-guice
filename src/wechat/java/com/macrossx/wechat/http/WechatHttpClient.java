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
package com.macrossx.wechat.http;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;

import lombok.extern.java.Log;

@Log
public class WechatHttpClient<T> {

	public Optional<T> send(@NotNull final HttpUriRequest request, Class<T> clazz) {
		String result = this.send(request);
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(new Gson().fromJson(result, clazz));
		}
	}

	public String send(@NotNull final HttpUriRequest request) {
		String result = "";
		try {
			CloseableHttpResponse response = HttpClients.createDefault().execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK != statusCode) {
				log.info("Failed to get!");
				return "";
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "utf-8");
		} catch (IOException e) {
			log.info(e.getMessage());
		}
		return result;
	}

}
