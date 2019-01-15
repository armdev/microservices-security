package io.project.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
public class ZuulTokenProvider {

    @Autowired
    private TimeProvider timeProvider;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${jwt.zuulSecret}")
    public String zuulSecret;

    @Value("${jwt.expires_in}")
    private int expiresIn;

    @Value("${jwt.mobile_expires_in}")
    private int mobileExpiresIn;

    @Value("${jwt.header}")
    private String authHeader;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public Date getExpirationFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getExpiration();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String refreshToken(String token, Device device) {
        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate(device))
                    .signWith(SIGNATURE_ALGORITHM, zuulSecret)
                    .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(String username, Device device) {
        log.info("SECRET IS " + " Really Secret");
        String audience = generateAudience(device);
        return Jwts.builder()
                .setIssuer(applicationName)
                .setSubject(username)
                .setAudience(audience)
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate(device))
                .signWith(SIGNATURE_ALGORITHM, zuulSecret)
                .compact();
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(zuulSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Device device) {
        long expiresInPeriod = device.isTablet() || device.isMobile() ? mobileExpiresIn : this.expiresIn;
        log.info("Set token exp time " + expiresInPeriod);
        return new Date(timeProvider.now().getTime() + expiresInPeriod * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? mobileExpiresIn : expiresIn;
    }

    public Boolean validateToken(String token) {

        final String username = getUsernameFromToken(token);
        if (username == null) {
            return false;
        }

        final Date created = getIssuedAtDateFromToken(token);

        final Date exiration = getExpirationFromToken(token);

        Date currentDate = timeProvider.now();
        if (exiration.getTime() > 0) {
            if (exiration.getTime() < currentDate.getTime()) {
                log.info("CrentDate.getTime() " + currentDate.getTime());
                log.info("Exiration.getTime() " + exiration.getTime());
                log.info("Token expired");
                return false;
            } else {
                log.info("You can use token");
                return true;
            }
        }
        return false;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

//    public String getTokenFromRequest(HttpServletRequest request) {
//        String authHeader = getAuthHeaderFromHeader(request);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }
//
//    public String getAuthHeaderFromHeader(HttpServletRequest request) {
//        return request.getHeader(AUTH_HEADER);
//    }
}
