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
package com.macrossx.template.groovy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.codehaus.groovy.control.CompilationFailedException;

import com.macrossx.template.groovy.entity.Page;
import com.macrossx.template.groovy.entity.signin.Signin01;

import groovy.text.SimpleTemplateEngine;
import lombok.extern.java.Log;
@Log
public class TemplateGroovyHelper implements ITemplateGroovyHelper {
	@Override
	public String simpleTemplate(Page page) {
		
		try {
			System.out.println(page.path() +File.separatorChar + page.name());
			File f = new File(page.path() + File.separatorChar + page.name());
			if (f.exists()){
				return new SimpleTemplateEngine()
						.createTemplate(new InputStreamReader(
								this.getClass().getResourceAsStream(page.path() + File.separatorChar + page.name())))
						.make(page.make()).toString();
			}else{
				return new SimpleTemplateEngine()
						.createTemplate(new InputStreamReader(new FileInputStream(this.getClass().getResource(page.path() + "" + page.name()).toString())))
						.make(page.make()).toString();
			}
			
		} catch (CompilationFailedException | IOException e) {
			// TODO Auto-generated catch block
			log.info(page.path()+File.separatorChar+page.name());
			e.printStackTrace();
			
			return "";
		}
		
	}

	
	public static void main(String...s){
		System.out.println(new TemplateGroovyHelper().simpleTemplate(new Signin01()));
	}
}
