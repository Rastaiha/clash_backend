package clash.back.controller;

import clash.back.domain.dto.LoginDto;
import clash.back.domain.dto.TokenDto;
import clash.back.domain.entity.Player;
import clash.back.service.PlayerService;
import clash.back.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    public JwtToken jwtToken;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PlayerService playerService;

    private void authenticate(String email, String password) throws BadCredentialsException, DisabledException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> createToken(@RequestBody LoginDto loginDto) throws Exception {
        authenticate(loginDto.getUsername(), loginDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
        Player user = playerService.getPlayerDetails(loginDto.getUsername());
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok((TokenDto) new TokenDto().toDto(token));
    }

}
