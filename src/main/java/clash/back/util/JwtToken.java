package clash.back.util;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;

public interface JwtToken extends Serializable {

    String getUsernameFromToken(String token);

    Boolean validateToken(String token, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    Date getExpirationDateFromToken(String token);

}
