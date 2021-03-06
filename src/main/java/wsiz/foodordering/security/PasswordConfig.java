package wsiz.foodordering.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class PasswordConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    //return NoOpPasswordEncoder.getInstance();
    return new BCryptPasswordEncoder(10);
  }

}
