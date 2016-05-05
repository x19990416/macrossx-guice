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

import com.google.gson.Gson;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.macrossx.wechat.entity.WechatMessageTemplate;
import com.macrossx.wechat.http.WechatHttpClient;

/**
 * module for wechat,needs constant bind with "macorssx.wechat.appid" &
 * "macrossx.wechat.appsercret"<br>
 * 1.macrossx.wechat.appid: wechat application id<br>
 * 2.macrossx.wechat.appsecret:wechat application secret<br>
 * 
 * 
 * {@code IWechatHelper}:wechat base function(Singleton)<br>
 * {@code IWechatMenuHelper}:wechat menu reference function<br>
 * 
 * 
 * @author starseeker.limin@gmail.com(X-Forever)
 */
public class WechatModule extends PrivateModule {

	@Override
	protected void configure() {
		super.bind(IWechatHelper.class).to(WechatHelper.class).asEagerSingleton();
		super.bind(IWechatMenuHelper.class).to(WechatMenuHelper.class);
		super.bind(IWechatMessageHelper.class).to(WechatMessageHelper.class);
		expose(IWechatHelper.class);
		expose(IWechatMenuHelper.class);
		expose(IWechatMessageHelper.class);
	}

	public static void main(String... s) {
		IWechatMessageHelper helper = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(WechatHttpClient.class);
				binder.bindConstant().annotatedWith(Names.named("macrossx.wechat.appid")).to("wxcd2ce9d306c24f4b");
				binder.bindConstant().annotatedWith(Names.named("macrossx.wechat.appsecret"))
						.to("a0ee51aabe13bfaf2bab1f6c9611a32e");
			}
		}, new WechatModule()).getInstance(IWechatMessageHelper.class);

		// WechatMessageTemplate tempalte = new WechatMessageTemplate();
		// tempalte.setTouser("oG_RsuBciQQik9vSD2W8hW8wkqr8");
		// tempalte.setTemplate_id(template_id);
		// WechatTempalteKeywordData k = new WechatTempalteKeywordData();
		//
		// tempalte.setData(data);
		// helper.sendTemplate(template);
		WechatMessageTemplate tempalte = new WechatMessageTemplate.KeywordBuilder()
				.touser("oG_RsuBciQQik9vSD2W8hW8wkqr8").templateId("7uzNMz58yLsSf10FWt0zDZllpjbLUJHxapNpzxLLbPs")
				.first("读者您好，你的图书已续借成功", "#FF000").keyword1("2014年7月21日 18:36", "#FF000").keyword2("1", "#FF000").remark("感谢你的使用。", "#000000")
				.build();

		System.out.println(new Gson().toJson(tempalte));
		System.out.println(helper.sendTemplate(tempalte));

	}
}
