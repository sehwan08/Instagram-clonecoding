package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;

	@Transactional
	public void subScribe(int fromUserId, int toUserId) {
		try {
			subscribeRepository.subScribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독 하셨습니다");
		}
	}

	@Transactional
	public void unsubScribe(int fromUserId, int toUserId) {
		subscribeRepository.unsubScribe(fromUserId, toUserId);
	}
}
