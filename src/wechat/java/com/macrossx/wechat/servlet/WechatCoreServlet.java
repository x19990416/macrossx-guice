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
package com.macrossx.wechat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.inject.Singleton;
import com.macrossx.wechat.IWechatServer;
import com.macrossx.wechat.entity.server.WechatHttpEntity;
import com.macrossx.wechat.entity.server.WechatImageRequest;
import com.macrossx.wechat.entity.server.WechatTextRequest;
import com.sun.istack.internal.Nullable;

import lombok.Data;
import lombok.extern.java.Log;

@Singleton
@Log
public class WechatCoreServlet extends HttpServlet {

	@Inject
	@Named("macrossx.wechat.token")
	private String wechatToken;

	@Inject
	@Nullable
	private IWechatServer wechatServer;

	private static final long serialVersionUID = -5494799653006464275L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (checkSignature(req)) {
				resp.getWriter().print(req.getParameter("echostr"));
				resp.getWriter().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (checkSignature(req)) {
				Optional<MessageType> messageType = phraseXml(req.getInputStream(), MessageType.class);
				WechatHttpEntity entity = null;
				if (messageType.isPresent()) {
					switch (messageType.get().getMsgType()) {
					case "text": {
						entity = wechatServer
								.onTextMessage(phraseXml(req.getInputStream(), WechatTextRequest.class).get());
						break;
					}
					case "image": {
						entity = wechatServer
								.onImageMessage(phraseXml(req.getInputStream(), WechatImageRequest.class).get());
						break;
					}
					}
				}
			}

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean checkSignature(final HttpServletRequest req) {
		String[] params = { wechatToken, req.getParameter("timestamp"), req.getParameter("nonce") };
		Arrays.sort(params);
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			builder.append(param);
		}
		return Hashing.sha1().hashString(builder.toString(), Charsets.UTF_8).equals(req.getParameter("signature"));

	}

	public static void main(String... s) throws NoSuchAlgorithmException, JAXBException {
		String sx = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456</MsgId></xml>";
		JAXBContext context = JAXBContext.newInstance(WechatImageRequest.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		WechatImageRequest teype = (WechatImageRequest) unmarshaller.unmarshal(new StringReader(sx));
		System.out.println(teype);

	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> phraseXml(InputStream xml, Class<T> clazz) {
		try {
			return (Optional<T>) Optional.of(JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return Optional.empty();
	}

	@XmlRootElement(name = "xml")
	@Data
	public static class MessageType {
		@XmlElement(name = "MsgType")
		private String MsgType;
	}

}
