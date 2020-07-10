package app.security;

import app.entity.ZUser;
import app.entity.ZUserDetails;
import app.repo.ZUserRepo;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
public class ZUserDetailsService implements UserDetailsService {

    private final ZUserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ZUser curr_user = repo.findByEmail(email).orElseThrow(RuntimeException::new);
        return new ZUserDetails(curr_user);
    }
}
