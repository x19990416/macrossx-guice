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
package com.macrossx.wechat.entity;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
public class WechatMessageTemplate extends WechatRequestObj {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Map<String, DataParam> data;

	public WechatMessageTemplate(KeywordBuilder builder) {
		touser = builder.touser;
		template_id = builder.template_id;
		url = builder.url;
		data = builder.data;
		topcolor = builder.topcolor;

	}

	public static class KeywordBuilder {
		private String topcolor;
		private String touser;
		private String template_id;
		private String url;
		private Map<String, DataParam> data;

		public KeywordBuilder() {
			data = Maps.newHashMap();
		}

		public KeywordBuilder touser(String openid) {
			touser = openid;
			return this;
		}
		public KeywordBuilder topcolor(String topcolor) {
			this.topcolor = topcolor;
			return this;
		}

		public KeywordBuilder templateId(String templateId) {
			template_id = templateId;
			return this;
		}

		public KeywordBuilder url(String url) {
			this.url = url;
			return this;
		}

		public KeywordBuilder first(String value, String color) {
			data.put("first", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder keyword1(String value, String color) {
			data.put("keyword1", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder keyword2(String value, String color) {
			data.put("keyword2", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder keyword3(String value, String color) {
			data.put("keyword3", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder keyword4(String value, String color) {
			data.put("keyword4", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder keyword5(String value, String color) {
			data.put("keyword5", new DataParam(value, color));
			return this;
		}

		public KeywordBuilder remark(String value, String color) {
			data.put("remark", new DataParam(value, color));
			return this;
		}

		public WechatMessageTemplate build() {
			return new WechatMessageTemplate(this);
		}

	}


	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DataParam {
		private String value;
		private String color;
	}
}
