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
package com.macrossx.template.groovy.entity.signin;

import java.util.Map;

import com.google.common.collect.Maps;
import com.macrossx.template.groovy.entity.Constants;
import com.macrossx.template.groovy.entity.Form;
import com.macrossx.template.groovy.entity.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper=false)
public class Signin01 extends Page {
	
	@Override
	public String name() {
		return "signin01.template";
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return Constants.PATH+"signin";
	}
	public Map<String,?> make() {
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("title", super.getTitle());
		Signin01Form form = new Signin01Form();
		form.setUserName("用户名");
		form.setUserNamePlaceHolder("用户名");
		form.setPassword("密码");
		form.setPasswordPlaceHolder("密码");
		form.setHeading("请登录");
		form.setSigninButton("登陆");
		map.put("form",form);
		map.put("icon","");
		return map;
	}
	@Data	
	@EqualsAndHashCode(callSuper=false)
	public static class Signin01Form extends Form{
		private String heading;
		private String userNamePlaceHolder;
		private String passwordPlaceHolder;
		private String signinButton;
		
	}
}
