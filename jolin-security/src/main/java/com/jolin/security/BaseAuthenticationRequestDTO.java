package com.jolin.security;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuthenticationRequestDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
    private String captchaKey;
    private String captchaCode;

    /**
     * Construct from the request
     *
     * @param request           http request
     * @param usernameParameter
     * @param passwordParameter
     */
    public BaseAuthenticationRequestDTO(HttpServletRequest request, String usernameParameter, String passwordParameter) {
        // The TODO verification code logic obtains the verification code parameters from the request
        //Obtain from parameter preferentially
        String username = request.getParameter(usernameParameter);
        if (StringUtils.isEmpty(username)) {
            //If not, get it in Json format from the body
            String json = getPostData(request);

            Map<String, String> map = JSONUtil.toBean(json, Map.class);
            this.username = map.get(usernameParameter);
            this.password = map.get(passwordParameter);
            this.captchaKey = map.get("captchaKey");
            this.captchaCode = map.get("captchaCode");
        } else {
            this.username = request.getParameter(usernameParameter);
            this.password = request.getParameter(passwordParameter);
            this.captchaKey = request.getParameter("captchaKey");
            this.captchaCode = request.getParameter("captchaCode");
        }
    }

    private static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }
}
