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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.inject.Singleton;
import com.macrossx.wechat.IWechatServer;
import com.macrossx.wechat.entity.server.WechatHttpEntity;
import com.macrossx.wechat.entity.server.WechatImageRequest;
import com.macrossx.wechat.entity.server.WechatLinkRequest;
import com.macrossx.wechat.entity.server.WechatLocationRequest;
import com.macrossx.wechat.entity.server.WechatSubscribeEventRequest;
import com.macrossx.wechat.entity.server.WechatTextRequest;
import com.macrossx.wechat.entity.server.WechatVideoRequest;
import com.macrossx.wechat.entity.server.WechatVoiceRequest;

import lombok.Data;
import lombok.extern.java.Log;

@Singleton
@Log
public class WechatCoreServlet extends HttpServlet {

	@Inject
	@Named("macrossx.wechat.token")
	private String wechatToken;

	@Inject
	private IWechatServer wechatServer;

	private static final long serialVersionUID = -5494799653006464275L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (checkSignature(req)) {
				resp.getWriter().write(req.getParameter("echostr"));
				resp.getWriter().flush();
				resp.getWriter().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			if (checkSignature(req)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i = -1;
				while ((i = req.getInputStream().read()) != -1) {
					baos.write(i);
				}
				log.info(baos.toString());
				MessageType messageType = phraseXml(baos.toString(), MessageType.class);
				WechatHttpEntity entity = null;
				if (messageType != null) {
					switch (messageType.getMsgType()) {
					case "text": {
						entity = wechatServer.onTextMessage(phraseXml(baos.toString(), WechatTextRequest.class));
						break;
					}
					case "image": {
						entity = wechatServer.onImageMessage(phraseXml(baos.toString(), WechatImageRequest.class));
						break;
					}
					case "voice": {
						entity = wechatServer.onVoiceMessage(phraseXml(baos.toString(), WechatVoiceRequest.class));
						break;
					}
					case "video": {
						entity = wechatServer.onVideoMessage(phraseXml(baos.toString(), WechatVideoRequest.class));
						break;
					}
					case "shortvideo": {
						entity = wechatServer.onShortVideoMessage(phraseXml(baos.toString(), WechatVideoRequest.class));
						break;
					}
					case "location": {
						entity = wechatServer
								.onLocationMessage(phraseXml(baos.toString(), WechatLocationRequest.class));
						break;
					}
					case "link": {
						entity = wechatServer.onLinkMessage(phraseXml(baos.toString(), WechatLinkRequest.class));
						break;
					}
					case "event": {
						switch (messageType.getEvent()) {
						case "subscribe": {
							entity = wechatServer
									.onEventSubscribe(phraseXml(baos.toString(), WechatSubscribeEventRequest.class));
							break;
						}
						case "unsubscribe": {
							entity = wechatServer
									.onEventUnsubscribe(phraseXml(baos.toString(), WechatSubscribeEventRequest.class));
							break;
						}
						}

						entity = wechatServer.onLinkMessage(phraseXml(baos.toString(), WechatLinkRequest.class));
						break;
					}
					}
				}
				System.out.println(entity);
				String result = phraseBean(entity);
				// log.info(result);
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(result);
				resp.getWriter().flush();
				resp.getWriter().close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean checkSignature(final HttpServletRequest req) {
		// System.out.println(wechatToken);
		// System.out.println(req.getParameter("timestamp"));
		// System.out.println(req.getParameter("nonce"));
		// System.out.println(req.getParameter("signature"));
		String[] params = { wechatToken, req.getParameter("timestamp"), req.getParameter("nonce") };
		Arrays.sort(params);
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			builder.append(param);
		}
		return Hashing.sha1().hashString(builder.toString(), Charsets.UTF_8).toString()
				.equals(req.getParameter("signature"));

	}

	public static void main(String... s) throws NoSuchAlgorithmException, JAXBException {
		String sx = "<xml><ToUserName><![CDATA[gh_78f77fba606c]]></ToUserName><FromUserName><![CDATA[o707et_rh-zHWLHKaSa9nwTnCkNo]]></FromUserName><CreateTime>1462938545</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[ji]]></Content><MsgId>6283273207240019156</MsgId></xml>";
		// "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456</MsgId></xml>";
		JAXBContext context = JAXBContext.newInstance(MessageType.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		MessageType teype = (MessageType) unmarshaller.unmarshal(new StringReader(sx));
		System.out.println(teype);

		// String[] params = { "wechattest", "1462935386", "1150661839" };
		// Arrays.sort(params);
		// StringBuilder builder = new StringBuilder();
		// for (String param : params) {
		// builder.append(param);
		// }
		//
		// System.out.println(Hashing.sha1().hashString(builder.toString(),
		// Charsets.UTF_8).toString()
		// .equals("197ea60d62301ee8b881717342cc1c1335590e78"));

	}

	@SuppressWarnings("unchecked")
	public <T> T phraseXml(String xml, Class<T> clazz) {
		try {
			return (T) JAXBContext.newInstance(clazz).createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return null;
	}

	private <T> String phraseBean(T t) {
		try {
			Marshaller marshaller = JAXBContext.newInstance(t.getClass()).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");//
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			marshaller.marshal(t, output);
			return output.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info(e.getMessage());
		}
		return "";
	}

	@XmlRootElement(name = "xml")
	@Data
	public static class MessageType {
		@XmlElement(name = "MsgType")
		private String MsgType;
		@XmlElement(name = "Event")
		private String Event;
	}
}
