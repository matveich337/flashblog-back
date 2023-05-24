package com.flasblog.back.service.implementations;

import com.flasblog.back.exceptions.exceptions.TokenException;
import com.flasblog.back.model.JwtTypeModel;
import com.flasblog.back.service.interfaces.TokenInterface;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** JwtServiceImpl. */
@Service
public class TokenImpl implements TokenInterface {
  @Value("${app.security.jwt.secret}")
  private byte[] secretKey;

  private final ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor =
      new DefaultJWTProcessor<>();

  /** Init method. */
  @PostConstruct
  public void init() {
    JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(secretKey);
    JWEKeySelector<SimpleSecurityContext> jweKeySelector =
        new JWEDecryptionKeySelector<>(
            JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
    jwtProcessor.setJWEKeySelector(jweKeySelector);
  }

  @Override
  public String createJwt(String email, JwtTypeModel type) {
    try {
      Date now = new Date();
      Date expDate =
          new Date(
              type.equals(JwtTypeModel.AUTHORIZATION)
                  ? (now.getTime() + 1000 * 60 * 60 * 24 * 10) // expires in 10 days
                  : (now.getTime() + 1000 * 60 * 10)); // expires in 10 minutes

      JWTClaimsSet claims =
          new JWTClaimsSet.Builder()
              .claim("email", email)
              .claim("type", type)
              .expirationTime(expDate)
              .notBeforeTime(now)
              .build();

      Payload payload = new Payload(claims.toJSONObject());

      JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);

      DirectEncrypter encrypter = new DirectEncrypter(secretKey);

      JWEObject jweObject = new JWEObject(header, payload);
      jweObject.encrypt(encrypter);
      return jweObject.serialize();
    } catch (JOSEException e) {
      throw new TokenException(Map.of("token", "Jwt creation error"), "Jwt unknown error");
    }
  }

  @Override
  public String getEmailFromJwt(String jwt) {
    return (String) getJwtClaimsSetFromToken(jwt).getClaim("email");
  }

  @Override
  public Boolean isAuthorizationToken(String jwt) {
    return getJwtClaimsSetFromToken(jwt).getClaim("type").equals(JwtTypeModel.AUTHORIZATION.name());
  }

  @Override
  public Boolean isEmailActivationToken(String jwt) {
    return getJwtClaimsSetFromToken(jwt)
        .getClaim("type")
        .equals(JwtTypeModel.EMAIL_ACTIVATION.name());
  }

  @Override
  public Boolean isPasswordForgottenToken(String jwt) {
    return getJwtClaimsSetFromToken(jwt)
        .getClaim("type")
        .equals(JwtTypeModel.PASSWORD_FORGOT.name());
  }

  private JWTClaimsSet getJwtClaimsSetFromToken(String jwt) {
    try {
      return jwtProcessor.process(jwt, null);
    } catch (BadJOSEException e) {
      throw new TokenException(
          Map.of(
              "token",
              (e.getMessage().equals("Expired JWT")) ? "Link expired" : "Jwt verification error"),
          "Jwt error");
    } catch (ParseException | JOSEException e) {
      throw new TokenException(Map.of("token", "Jwt verification error"), "Jwt unknown error");
    }
  }
}
