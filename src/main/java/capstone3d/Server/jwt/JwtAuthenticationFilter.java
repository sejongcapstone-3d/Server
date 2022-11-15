package capstone3d.Server.jwt;

import capstone3d.Server.domain.dto.Subject;
import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.service.UserDetailsService;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!Objects.isNull(authorization)) {
            String atk = authorization.substring(7);
            try {
                Subject subject = jwtTokenProvider.getSubject(atk);
                String requestURI = request.getRequestURI();
                if (subject.getType().equals("RTK") && !requestURI.equals("/reissue")) {
                    throw new JwtException("토큰을 확인하세요.");
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(subject.getEmail());
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}