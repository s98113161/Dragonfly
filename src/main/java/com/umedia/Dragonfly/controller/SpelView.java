package com.umedia.Dragonfly.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.web.servlet.View;

/**
 * Simple String template renderer.
 * 
 */
class SpelView implements View {

	private final String template;

	private final SpelExpressionParser parser = new SpelExpressionParser();

	private final StandardEvaluationContext context = new StandardEvaluationContext();

	private PropertyPlaceholderHelper helper;

	private PlaceholderResolver resolver;

	public SpelView(String template) {
		this.template = template;
		this.context.addPropertyAccessor(new MapAccessor());
		this.helper = new PropertyPlaceholderHelper("${", "}");
		this.resolver = new PlaceholderResolver() {
			public String resolvePlaceholder(String name) {
				Expression expression = parser.parseExpression(name);
				Object value = expression.getValue(context);
				return value == null ? null : value.toString();
			}
		};
	}

	public String getContentType() {
		return "text/html";
	}

	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(model);
		map.put("path", (Object) request.getContextPath());
		context.setRootObject(map);
		String result = helper.replacePlaceholders(template, resolver);
		response.setContentType(getContentType());
		response.getWriter().append(result);
	}
}
