package com.cos.photogramstart.service;


import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User profile(int userId) {
		//SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(userId).orElseThrow(()->
		{
			throw new CustomException("해당 프로파일은 없습니다.");
		});
		System.out.println("=========================================");
		return userEntity;
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
