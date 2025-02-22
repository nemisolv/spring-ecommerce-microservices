package net.nemisolv.identity.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.entity.Permission;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.payload.auth.IntrospectRequest;
import net.nemisolv.identity.payload.auth.IntrospectResponse;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.properties.JWTTokenProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {



    private final JWTTokenProperties tokenProperties;
    private final UserRepository userRepository;

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractTokenExpire(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken( UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails, tokenProperties.getTokenExpireTime());
    }

    public String generateRefreshToken(UserDetails userDetails) {

        return buildToken(new HashMap<>(),userDetails, tokenProperties.getRefreshTokenExpireTime());
    }


    // because other services use nimbus-jose-jwt library, so we need to custom the token
    public String buildScope(UserDetails userDetails) {
        StringJoiner joiner = new StringJoiner(" ");
//        Role role = userPrincipal.getRole();
//        Set<Permission> permissions = role.getPermissions();
//
//        joiner.add("ROLE_" + role.getName().name());
//        permissions.forEach(permission -> joiner.add(permission.getName().name()));

        userDetails.getAuthorities().forEach(authority -> {
            if(authority instanceof SimpleGrantedAuthority) {
                joiner.add(authority.getAuthority());
            }
        });



        return joiner.toString();
    }



    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails, tokenProperties.getTokenExpireTime());
    }

    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails, tokenProperties.getRefreshTokenExpireTime());
    }

    public String generateTokenWithExpire(UserDetails userDetails, long expire) {
        return buildToken(new HashMap<>(),userDetails, expire);
    }




    private String buildToken(Map<String,Object> extraClaims, UserDetails userDetails, long expire) {
        return Jwts.builder()
//                .setClaims(extraClaims)
                .setClaims(Map.of("scope", buildScope(userDetails)))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire) )
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }






    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = extractSubject(token);
        return username.equals(userDetails.getUsername()) && extractTokenExpire(token).after(new Date());
    }

    private Key getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(tokenProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public IntrospectResponse introspectToken(IntrospectRequest request) {
        String token = request.token();
        String username = extractSubject(token);
        Optional<User> user = userRepository.findByEmail(username);
        boolean isValid = false;
        if(user.isEmpty()) {
            return new IntrospectResponse(isValid);
        }
        isValid = isValidToken(token, UserPrincipal.create(user.get()));
        return new IntrospectResponse(isValid);
    }

    public long getTokenExpireTime(String token) {
        return extractTokenExpire(token).getTime();
    }
}