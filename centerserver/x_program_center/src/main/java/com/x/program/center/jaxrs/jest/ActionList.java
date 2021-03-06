package com.x.program.center.jaxrs.jest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.x.base.core.project.Application;
import com.x.base.core.project.gson.GsonPropertyObject;
import com.x.base.core.project.http.ActionResult;
import com.x.program.center.ThisApplication;

class ActionList extends BaseAction {

	ActionResult<List<Wo>> execute(HttpServletRequest request, String source) throws Exception {
		ActionResult<List<Wo>> result = new ActionResult<>();
		List<Wo> wos = new ArrayList<>();
		Boolean fromProxy = this.formProxy(request, source);
		for (Entry<String, CopyOnWriteArrayList<Application>> en : ThisApplication.context().applications()
				.entrySet()) {
			Wo wo = new Wo();
			wo.setName(en.getKey());
			wo.setUrlList(new ArrayList<String>());
			for (Application o : en.getValue()) {
				if (fromProxy) {
					if (this.isUndefindHost(o.getProxyHost())) {
						wo.getUrlList().add("http://" + this.getHost(request) + ":" + o.getProxyPort() + o.getContext()
								+ "/jest/index.html");
					} else {
						wo.getUrlList().add("http://" + o.getProxyHost() + ":" + o.getProxyPort() + o.getContext()
								+ "/jest/index.html");
					}
				} else {
					wo.getUrlList()
							.add("http://" + o.getNode() + ":" + o.getPort() + o.getContext() + "/jest/index.html");
				}
			}
			wos.add(wo);
			wos = wos.stream().sorted(Comparator.comparing(Wo::getName, Comparator.nullsLast(String::compareTo)))
					.collect(Collectors.toList());
		}
		result.setData(wos);
		return result;
	}

	public static class Wo extends GsonPropertyObject {
		private String name;
		private List<String> urlList;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getUrlList() {
			return urlList;
		}

		public void setUrlList(List<String> urlList) {
			this.urlList = urlList;
		}

	}

}