package toy.com.config.auth;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import toy.com.exception.CustomException;
import toy.com.exception.code.ErrorCode;
import toy.com.user.domain.User;
import toy.com.user.repository.UserRepository;
import toy.com.user.service.TokenService;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

	private final TokenService tokenService;
	private final UserRepository userRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserAuthentication.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest request, WebDataBinderFactory binderFactory) {

		String header = request.getHeader(AuthEnum.HEADER.getValue());
		if (header == null || !header.startsWith(AuthEnum.TOKEN_TYPE.getValue())) {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}

		Claims accessClaim = tokenService.validate(header.replace(AuthEnum.TOKEN_TYPE.getValue(), ""));

		User user = userRepository.findById(Long.valueOf(accessClaim.get("id").toString()))
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
		return new UserAuthentication(user.getId());
	}
}
