package capstone3d.Server.jwt;

import capstone3d.Server.domain.dao.RedisDao;
import capstone3d.Server.domain.dto.Subject;
import capstone3d.Server.domain.dto.response.TokenResponse;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @Value("${spring.jwt.key}")
    private String secretKey;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;

    private Key key;

    @PostConstruct
    protected void init() {
//        key = Base64.getEncoder().encodeToString(key.getBytes());
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
//        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public TokenResponse createTokensByLogin(UserResponse userResponse) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                userResponse.getId(),
                userResponse.getIdentification(),
                userResponse.getNickname());
        Subject rtkSubject = Subject.rtk(
                userResponse.getId(),
                userResponse.getIdentification(),
                userResponse.getNickname());

        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);
        redisDao.setValues(userResponse.getIdentification(), rtk, Duration.ofMillis(rtkLive));
        return new TokenResponse(atk, rtk);
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenResponse reissueAtk(UserResponse userResponse) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(userResponse.getIdentification());
        if(Objects.isNull(rtkInRedis)) throw new ForbiddenException("인증 정보가 만료되었습니다.");
        Subject atkSubject = Subject.atk(
                userResponse.getId(),
                userResponse.getIdentification(),
                userResponse.getNickname());
        String atk = createToken(atkSubject, atkLive);
        return new TokenResponse(atk, null);
    }
}