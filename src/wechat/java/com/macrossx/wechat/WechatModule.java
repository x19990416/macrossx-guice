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
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.PrivateModule;
import com.google.inject.name.Names;
import com.macrossx.wechat.entity.WechatMessageTemplate;
import com.macrossx.wechat.http.WechatHttpClient;
import com.macrossx.wechat.impl.WechatHelper;
import com.macrossx.wechat.impl.WechatMenuHelper;
import com.macrossx.wechat.impl.WechatMessageHelper;
import com.macrossx.wechat.impl.WechatUserHelper;

import lombok.Data;

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
		super.bind(IWechatUserHelper.class).to(WechatUserHelper.class);
		expose(IWechatHelper.class);
		expose(IWechatMenuHelper.class);
		expose(IWechatMessageHelper.class);
		expose(IWechatUserHelper.class);
	}

//	public static void main(String... s) throws URISyntaxException {
//		Injector injector = Guice.createInjector(new Module() {
//			@Override
//			public void configure(Binder binder) {
//				binder.bind(WechatHttpClient.class);
//				binder.bindConstant().annotatedWith(Names.named("macrossx.wechat.appid")).to("wx15eb10da58e4f82a");
//				binder.bindConstant().annotatedWith(Names.named("macrossx.wechat.appsecret"))
//						.to("3fc8e5e23a4155df91150c9e48475759");
//			}
//		}, new WechatModule());
//		
//		IWechatMessageHelper helper = injector.getInstance(IWechatMessageHelper.class);
//		System.out.println(helper.sendTemplate(new WechatMessageTemplate.KeywordBuilder()
//				.touser("oMY5RwXTBxuposnpePnHesBmACY4").templateId("X0gbk0uNvqfHEX7KiBabuRHfC8wpH25VAi6eOQZHs4M")
//				.first("aaaa", "#FF00").keyword1("BBBB", "#FF00").keyword2("cccc", "#FF00")
//				.remark("xxxxxxxxxxxxxx", "#FF00").build()));
//		
//		IWechatHelper whelper = injector.getInstance(IWechatHelper.class);
//		String token = whelper.getAccessToken().get().getAccess_token();
//		String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid=o7r5axMPJrWV6Q2bgPa5QnmdP45E&lang=zh_CN";
//		HttpGet httpGet = new HttpGet();
//		System.out.println(MessageFormat.format(url, token));
//		httpGet.setURI(new URI(MessageFormat.format(url, token)));
//		System.out.println( new WechatHttpClient<Entiry>().send(httpGet, Entiry.class));
//		IWechatUserHelper uhelper = injector.getInstance(IWechatUserHelper.class);
//		uhelper.userGet(null).ifPresent((e)->{
//			List<String> l = e.getData().getOpenid();
//			
//			for(int i=l.size()-1;i>=0;i--){
//				uhelper.userInfo(l.get(i)).ifPresent((ex)->{
//					System.out.println(ex);
//					if(ex.getUnionid().equals("omnN5xNeI24bd9uGbeynyxybkfyU")){
//						System.out.println(ex);
//					}
//				});
//			}
//			
//			
//			for(String sx:l){
//				//System.out.println(sx);
//
//			}
//			
//		});
//		// WechatMessageTemplate tempalte = new WechatMessageTemplate();
//		// tempalte.setTouser("oG_RsuBciQQik9vSD2W8hW8wkqr8");
//		// tempalte.setTemplate_id(template_id);
//		// WechatTempalteKeywordData k = new WechatTempalteKeywordData();
//		//
//		// tempalte.setData(data);
//		// helper.sendTemplate(template);
//
//	}
//	@Data	
//	public static class Entiry{
//		String openid;
//		String nickname;
//		int errcode;
//		String errmsg;
//	}
}
