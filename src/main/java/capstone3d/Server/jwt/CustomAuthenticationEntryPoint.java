package capstone3d.Server.jwt;

import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(StatusMessage.Unauthorized.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        AllResponse allResponse = new AllResponse<>(StatusMessage.Unauthorized.getStatus()
                , StatusMessage.Unauthorized.getMessage(), 0, null);
        response.getWriter().print(this.convertObjectToJson(allResponse));
    }

    private String convertObjectToJson(Object o) throws JsonProcessingException {
        return o == null ? null : objectMapper.writeValueAsString(o);
    }
}