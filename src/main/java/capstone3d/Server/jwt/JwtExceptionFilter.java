package capstone3d.Server.jwt;

import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    public JwtExceptionFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BadRequestException e) {
            setErrorResponse(response, e);
        }
    }
    public void setErrorResponse(HttpServletResponse response, BadRequestException e) throws IOException {
        response.setStatus(e.getStatusMessage().getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        AllResponse allResponse = new AllResponse<>(e.getStatusMessage().getStatus()
                , e.getStatusMessage().getMessage(), 0, null);
        response.getWriter().print(this.convertObjectToJson(allResponse));
    }

    private String convertObjectToJson(Object o) throws JsonProcessingException {
        return o == null ? null : objectMapper.writeValueAsString(o);
    }
}
