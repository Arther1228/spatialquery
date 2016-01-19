package com.suncreate.spatialquery.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionServlet;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ebiz.ssi.web.struts.BaseSsiAction;
import com.suncreate.spatialquery.service.Facade;


/**
 * @author Cheng,Zhi
 */
public abstract class BaseAction extends BaseSsiAction {

	private Facade facade;

	/**
	 * the powerful method to return facade with all services(method)
	 * 
	 * @return facade
	 */
	
	protected Facade getFacade() {
		return this.facade;
	}


	@Override
	public void setServlet(ActionServlet actionServlet) {
		super.setServlet(actionServlet);
		Assert.notNull(actionServlet, "actionServlet is can not be null");
		ServletContext servletContext = actionServlet.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.facade = (Facade) wac.getBean("facade");
	}

	protected Object getSessionAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		return (session != null ? session.getAttribute(name) : null);
	}


}