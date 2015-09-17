package com.umedia.Dragonfly.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth.examples.sparklr.oauth.SparklrUserApprovalHandler;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.umedia.Dragonfly.oauth.DragonflyUserApprovalHandler;

@Configuration
public class OAuthServerConfig {
	private static final String RESOURCE_ID = "cpe";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID).stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
				.requestMatchers().antMatchers("/device/**", "/oauth/users/**", "/oauth/clients/**","/me")
			.and()
				.authorizeRequests()				
					.antMatchers("/me").access("#oauth2.hasScope('read')")					
					.antMatchers("/device").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")                                        
					.antMatchers("/device/trusted/**").access("#oauth2.hasScope('trust')")
					.antMatchers("/device/user/**").access("#oauth2.hasScope('trust')")					
					.antMatchers("/device/**").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
					//.antMatchers("/device/**").access("#oauth2.hasScope('write') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
					.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
						.access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");					
			// @formatter:on
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		//@Autowired
		//private DataSource dataSource;

		@Autowired
		private TokenStore tokenStore;

		@Autowired
		private UserApprovalHandler userApprovalHandler;

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		// needs to be change
		@Value("${tonr.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
		private String tonrRedirectUri;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {

			// JdbcClientDetailsServiceBuilder
			// @formatter:off
			//clients.jdbc(dataSource);
			
			clients.inMemory().withClient("tonr")
			 			.resourceIds(RESOURCE_ID)
			 			.authorizedGrantTypes("authorization_code", "implicit")
			 			.authorities("ROLE_CLIENT")
			 			.scopes("read", "write")
			 			.secret("secret")
			 		.and()
			 		.withClient("tonr-with-redirect")
			 			.resourceIds(RESOURCE_ID)
			 			.authorizedGrantTypes("authorization_code", "implicit")
			 			.authorities("ROLE_CLIENT")
			 			.scopes("read", "write")
			 			.secret("secret")
			 			.redirectUris(tonrRedirectUri)
			 		.and()
		 		    .withClient("my-client-with-registered-redirect")
	 			        .resourceIds(RESOURCE_ID)
	 			        .authorizedGrantTypes("authorization_code", "client_credentials")
	 			        .authorities("ROLE_CLIENT")
	 			        .scopes("read", "trust")
	 			        .redirectUris("http://anywhere?key=value")
		 		    .and()
	 		        .withClient("my-trusted-client")
 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
 			            .scopes("read", "write", "trust")
 			            .accessTokenValiditySeconds(60)
		 		    .and()
	 		        .withClient("my-trusted-client-with-secret")
 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
 			            .scopes("read", "write", "trust")
 			            .secret("somesecret")
	 		        .and()
 		            .withClient("my-less-trusted-client")
			            .authorizedGrantTypes("authorization_code", "implicit")
			            .authorities("ROLE_CLIENT")
			            .scopes("read", "write", "trust")
     		        .and()
		            .withClient("my-less-trusted-autoapprove-client")
		                .authorizedGrantTypes("implicit")
		                .authorities("ROLE_CLIENT")
		                .scopes("read", "write", "trust")
		                .autoApprove(true);
			// @formatter:on
		}

		@Bean
		public TokenStore tokenStore() {
			 return new InMemoryTokenStore();
			//return new JdbcTokenStore(dataSource);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints)
				throws Exception {
			endpoints.tokenStore(tokenStore)
					.userApprovalHandler(userApprovalHandler)
					.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)
				throws Exception {
			oauthServer.realm("dragonfly/client");
		}

	}

	protected static class Stuff {

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private TokenStore tokenStore;

		@Bean
		public ApprovalStore approvalStore() throws Exception {
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore);
			return store;
		}

		@Bean
		@Lazy
		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
		public DragonflyUserApprovalHandler userApprovalHandler()
				throws Exception {
			DragonflyUserApprovalHandler handler = new DragonflyUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(
					clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
	}
}
