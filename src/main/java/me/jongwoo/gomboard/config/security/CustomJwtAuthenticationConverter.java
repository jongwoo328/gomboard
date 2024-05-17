package me.jongwoo.gomboard.config.security;

import lombok.extern.slf4j.Slf4j;
import me.jongwoo.gomboard.domains.user.constant.JwtCustomClaimName;
import me.jongwoo.gomboard.domains.user.constant.JwtType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Slf4j
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtAuthenticationConverter defaultConverter;

    public CustomJwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.defaultConverter = new JwtAuthenticationConverter();
        this.defaultConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String customTypeValue = jwt.getClaimAsString(JwtCustomClaimName.TYPE);
        if (customTypeValue.equals(JwtType.ACCESS)) {
            return defaultConverter.convert(jwt);
        }
        throw new IllegalArgumentException("Invalid token type");
    }
}
