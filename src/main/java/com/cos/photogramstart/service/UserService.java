package com.cos.photogramstart.service;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional(readOnly = true)
	public UserProfileDto profile(int pageUserId, int principalId) {
		//SELECT * FROM image WHERE userId = :userId;
		UserProfileDto dto = new UserProfileDto();
		
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->
		{
			throw new CustomException("해당 프로파일은 없습니다.");
		});
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId);
		dto.setImageCount(userEntity.getImages().size());
		return dto;
	}
	
	
	@Transactional
	public User update(int id, User user) {
		
		//1.영속화
		User userEntity = userRepository.findById(id)
				.orElseThrow(()-> {return new CustomValidationApiException("Not found");});
		//2. 영속화된 객체를 더티체킹
		userEntity.setName(user.getName());
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;
	}
}
