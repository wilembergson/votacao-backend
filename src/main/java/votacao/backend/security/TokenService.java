package votacao.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.entity.Usuario;
import votacao.backend.security.dto.ValidateTokenDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usr) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("votacao-api")
                    .withSubject(usr.getLogin())
                    /*.withClaim(
                            "cpf",
                            (usr.getCpf() != null) ? usr.getCpf() : 00000000000
                    )*/
                    .withClaim("role", usr.getRole())
                    .withExpiresAt(expirationTime())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token.", exception);
        }
    }

    public ValidateTokenDTO validateToken(String token) {
        DecodedJWT decodedJWT = getDecodeJWT(token);
        String login = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("role").asString();
        return new ValidateTokenDTO(login, role);
    }

    /*public Long getUserCpf(String requestToken) {
        String token = requestToken.replace("Bearer ", "");
        DecodedJWT decodedJWT = getDecodeJWT(token);
        return decodedJWT.getClaim("cpf").asLong();
    }*/

    public String getLogin(String requestToken) {
        String token = requestToken.replace("Bearer ", "");
        DecodedJWT decodedJWT = getDecodeJWT(token);
        return decodedJWT.getSubject();
    }

    private DecodedJWT getDecodeJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("votacao-api")
                    .build()
                    .verify(token);
        } catch (TokenExpiredException e) {
            throw new CustomException("Sessão expirada.", HttpStatus.FORBIDDEN);
        } catch (JWTDecodeException e) {
            throw new CustomException("Token inválido.", HttpStatus.FORBIDDEN);
        }
    }

    private Instant expirationTime() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
