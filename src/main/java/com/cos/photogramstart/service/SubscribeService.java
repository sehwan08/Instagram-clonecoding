package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> subscribeList(int principalId, int pageUserId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
		sb.append("if((?=u.id), 1, 0)equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");
		
		//첫번째 물음표 principalId
		//중간 물음표 principalId
		//마지막 물음표 pageUserId
		
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDto = result.list(query, SubscribeDto.class);
		
		return subscribeDto;
	}
	
	
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
