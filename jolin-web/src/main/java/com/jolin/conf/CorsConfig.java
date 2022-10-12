package com.jolin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // Allow all methods including "GET", "POST", "DELETE", "PUT", etc
        corsConfiguration.setMaxAge(1800l);//30 minutes

        //Using setAllowCredentials to resolve cross-domain problems only supports ie10 and above.
        //If you use the default way of coordinate CookierHttpSessionStrategy session
        //The front and back ends are turned on together, and when turned on, the browser's Cookies can be read and written. This field is optional. Its value is a Boolean value indicating whether cookies are allowed to be sent.
        //By default, cookies are not included in CORS requests. Set to true to indicate that the server has given explicit permission,
        //Cookies can be included in the request and sent to the server together. This value can only be set to true. If the server does not want the browser to send cookies, delete this field.
        corsConfiguration.setAllowCredentials(true);
        //Cooperate with HeaderHttpSessionStrategy way of the session. token is used to solve cross-domain problems.
        // CORS request. The getResponseHeader() method of the XMLHttpRequest object gets only six basic fields: Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma. If I want to get any other fields,
        //You must specify that in Access-Control-Expose-Headers. The example above specifies that getResponseHeader(' FooBar ') can return the value of the FooBar field.
        //Allows clienHeaderWriterFiltert-site to obtain the customized header value
        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
