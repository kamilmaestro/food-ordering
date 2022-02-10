package wsiz.foodordering

import wsiz.foodordering.user.domain.CustomUserDetails
import wsiz.foodordering.user.dto.UserDto
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

trait SecurityContextProvider {

  void logInUser(UserDto user) {
    CustomUserDetails authenticatedUser = CustomUserDetails.builder()
        .userId(user.userId)
        .username(user.username)
        .build()
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(authenticatedUser, null)
    )
  }

}
