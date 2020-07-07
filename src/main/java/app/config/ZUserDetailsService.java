package app.config;

import app.entity.ZUser;
import app.entity.ZUserDetails;
import app.repo.ZUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
public class ZUserDetailsService implements UserDetailsService {
    private final ZUserRepo repo;

    public ZUserDetailsService(ZUserRepo repo) {
        this.repo = repo;
    }

    public static UserDetails mapperToUserDetails(ZUser user) {
        return new ZUserDetails(user.email, user.password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email)
                .map(ZUserDetailsService::mapperToUserDetails)
                .orElseThrow(() -> {
                    String msg = String.format("User with `%s` email is not found in the database", email);
                    log.warn(msg);
                    return new UsernameNotFoundException(msg);
                });
    }
}
