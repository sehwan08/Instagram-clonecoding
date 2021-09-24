package com.cos.photogramstart.web.dto.user;


import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	//필수
	@NotBlank
	private String name;
	@NotBlank
	private String password;
	//안필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//필수가 아닌 데이터를 받는 엔티티는 위험함
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
