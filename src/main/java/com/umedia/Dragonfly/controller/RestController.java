package com.umedia.Dragonfly.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

//this is protected by oauth
@Controller
@SessionAttributes("authorizationRequest")
// @SessionAttributes(types = AuthorizationRequest.class)
// @RequestMapping("/service")
public class RestController {

	
	@RequestMapping("/oauth/confirm_access")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}
		@SuppressWarnings("unchecked")
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes")
				: request.getAttribute("scopes"));
		model.put("scopes", scopes);
		model.put("path", (Object) request.getContextPath());
		return new ModelAndView("test", model);

	}
	  /*
//這個mapping沒有對應，不會呼叫，參考用
	@RequestMapping("/oauth/confirm_access2")
	public ModelAndView getAccessConfirmation2(Map<String, Object> model, HttpServletRequest request) throws Exception {
		String template = createTemplate(model, request);
		if (request.getAttribute("_csrf") != null) {
			model.put("_csrf", request.getAttribute("_csrf"));
		}
		return new ModelAndView(new SpelView(template), model);
	} 

	private String createTemplate(Map<String, Object> model, HttpServletRequest request) {
		String template = TEMPLATE;
		if (model.containsKey("scopes") || request.getAttribute("scopes") != null) {
			template = template.replace("%scopes%", createScopes(model, request)).replace("%denial%", "");
		} else {
			template = template.replace("%scopes%", "").replace("%denial%", DENIAL);
		}
		if (model.containsKey("_csrf") || request.getAttribute("_csrf") != null) {
			template = template.replace("%csrf%", CSRF);
		} else {
			template = template.replace("%csrf%", "");
		}
		return template;
	}

	private CharSequence createScopes(Map<String, Object> model, HttpServletRequest request) {
		StringBuilder builder = new StringBuilder("<ul>");
		@SuppressWarnings("unchecked")
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes")
				: request.getAttribute("scopes"));
		for (String scope : scopes.keySet()) {
			System.out.println("scope:"+scope);
			String approved = "true".equals(scopes.get(scope)) ? " checked" : "";
			String denied = !"true".equals(scopes.get(scope)) ? " checked" : "";
			String value = SCOPE.replace("%scope%", scope).replace("%key%", scope).replace("%approved%", approved)
					.replace("%denied%", denied);
			builder.append(value);
		}
		builder.append("</ul>");
		return builder.toString();
	}

	private static String CSRF = "<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}' />";

	private static String DENIAL = "<form id='denialForm' name='denialForm' action='${path}/oauth/authorize' method='post'><input name='user_oauth_approval' value='false' type='hidden'/>%csrf%<label><input name='deny' value='Deny' type='submit'/></label></form>";

	private static String TEMPLATE = "<html><body><h1>This is annother OAuth Approval</h1>"
			+ "<p>Do you authorize '${authorizationRequest.clientId}' to access your protected resources?</p>"
			+ "<form id='confirmationForm' name='confirmationForm' action='${path}/oauth/authorize' method='post'><input name='user_oauth_approval' value='true' type='hidden'/>%csrf%%scopes%<label><input name='authorize' value='Authorize' type='submit'/></label></form>"
			+ "%denial%</body></html>";

	private static String SCOPE = "<li><div class='form-group'>%scope%: <input type='radio' name='%key%'"
			+ " value='true'%approved%>Approve</input> <input type='radio' name='%key%' value='false'%denied%>Deny</input></div></li>";

	*/
	
	@RequestMapping(value = "/device", params = "format=json")
	public ResponseEntity<String> getDeviceJson(@RequestParam(value = "callback", required = false) String callback,
			Principal principal) {
		ArrayList<String> devicelist = new ArrayList<String>();
		devicelist.add("first device");

		StringBuilder out = new StringBuilder();
		System.out.println("out:" + out);
		if (callback != null) {
			out.append(callback).append("( ");
		}
		out.append("{ \"device\" : [ ");
		Iterator<String> photosIt = devicelist.iterator();
		while (photosIt.hasNext()) {
			String device = photosIt.next();
			out.append(String.format("{ \"id\" : \"%s\" }", device));
			if (photosIt.hasNext()) {
				out.append(" , ");
			}
		}
		out.append("] }");
		if (callback != null) {
			out.append(" )");
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/javascript");
		return new ResponseEntity<String>(out.toString(), headers, HttpStatus.OK);
	}

}
