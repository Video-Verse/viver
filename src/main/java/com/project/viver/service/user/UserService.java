package com.project.viver.service.user;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.viver.common.constraint.CommonId;
import com.project.viver.common.constraint.Role;
import com.project.viver.common.constraint.UserType;
import com.project.viver.common.constraint.oauth.OAuthAttributes;
import com.project.viver.dto.user.NicknameRequest;
import com.project.viver.entity.user.User;
import com.project.viver.error.ErrorCode;
import com.project.viver.error.exception.AuthenticationException;
import com.project.viver.error.exception.BusinessException;
import com.project.viver.error.exception.EntityNotFoundException;
import com.project.viver.repository.user.UserRepository;
import com.project.viver.service.common.CommonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
	CommonService commonService;

    public User registerUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByUserId(user.getUserId());
        if(optionalUser.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public User findUserByRefreshToken(String refreshToken) {
    	User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = user.getTokenExpirationTime();
        if(tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return user;
    }

    public User findUserByUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));
    }

	public ResponseEntity<String> registerNickname(NicknameRequest request) {
		String nickname = request.getNickname();
		User user = null;
		user =  userRepository.findByNickName(nickname);
		System.out.println(request.getNickname());
		
		//닉네임 중복 체크
		if(user != null) {
			return new ResponseEntity<>("duplicate", HttpStatus.BAD_REQUEST);
		} else {
			user = User.builder()
			           .userId(commonService.getId(CommonId.USER.value()))
			           .nickName(nickname)
			           .build();
     
			registerUser(user);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);		
	}
	
	/**
	 * kakao로 찾기
	 * 
	 * @param kakaoEmail
	 * @return
	 */
	@Transactional(readOnly = true)
    public Optional<User> findByKakao(String kakaoEmail) {
        return userRepository.findByKakao(kakaoEmail);
    }

//    public Object doSocialLogin(@Valid SocialLoginRequest request) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'doSocialLogin'");
//    }
}