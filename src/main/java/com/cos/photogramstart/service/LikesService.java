package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesService {
	
	private final LikesRepository likesRepository;
	
	@Transactional
	public void likes(int imageId, int principalId) {
		likesRepository.likes(imageId, principalId);
	}
	
	@Transactional
	public void unlikes(int imageId, int principalId) {
		likesRepository.unlikes(imageId, principalId);
	}
}
