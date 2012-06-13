/**
 * Copyright (C) 2012 tachibanakikaku.com Limited.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tachibanakikaku.webscripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class HelloCI extends DeclarativeWebScript {

	private static int RANDOM_MAX = 100;

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("result", getResultMessage());

		return model;
	}

	private String getResultMessage() {
		Random random = new Random(System.currentTimeMillis());
		int r = random.nextInt(RANDOM_MAX);
		String f = "Your number is %s.";
		String m = "";
		m = String.format(f, new Object[] { r });
		return m;
	}
}
