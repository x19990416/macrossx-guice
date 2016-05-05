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

import java.util.List;
import java.util.Optional;

import com.macrossx.wechat.entity.WechatButton;
import com.macrossx.wechat.entity.WechatMenu;

public interface IWechatMenuHelper {
	/**
	 * create menu
	 * 
	 * @param menu
	 *            menu to create
	 *            {@link https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN}
	 * @return true sucess
	 */
	public boolean createMenu(List<WechatButton> menu);

	public boolean deleteMenu();
	
	public Optional<WechatMenu> getMenu();

}
