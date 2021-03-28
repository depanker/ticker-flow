package com.depanker.ticker.security.config;

import com.depanker.ticker.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.depanker.ticker.security.constants.SecurityConstants.SECRET;
import static java.util.Collections.emptyList;


@Slf4j
@Data
public class TokenAuthenticationService {

    /**
     * This method will be used adding authentication to response header
     * @param req
     * @param res
     * @param username
     * @throws IOException
     */

	  public static void addAuthentication(HttpServletRequest req, HttpServletResponse res, String username, String url) throws IOException {


		  /* build jwt token */
		  String JWT = Jwts.builder()
				  .setSubject(username)
				  //.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATIONTIME))
				  .signWith(SignatureAlgorithm.HS512, SECRET)
				  .compact();
		  /* add header to response */
		  res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + " " + JWT);
		  /* set user details in response */
		  res.getWriter().flush();
	      res.getWriter().close();
	  }

    /**
     * This method will be used to get user from authentication token
     * @param request
	 * @return Authentication
     */
	  public static Authentication getAuthentication(HttpServletRequest request) {
	    String token = request.getHeader(SecurityConstants.HEADER_STRING);
	    if (token != null) {
	      // parse the token.
	      String user = Jwts.parser()
	          .setSigningKey(SECRET)
	          .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
	          .getBody()
	          .getSubject();
	      return user != null ?
	          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
	          null;
	    }
	    return null;
	  }
}
