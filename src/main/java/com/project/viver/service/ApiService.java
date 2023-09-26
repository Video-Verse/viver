package com.project.viver.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.viver.entity.kopis.Musical;
import com.project.viver.service.api.KopisService;

@Service
public class ApiService {
	
	@Autowired
	KopisService kopisService;
	
	/**
	 * 검색
	 * 
	 * @param params
	 * @return
	 * @throws ParseException 
	 */
	public List<Map<String,Object>> search (Map<String,Object> params ) throws ParseException {
		//1. db에 먼저 찾아보기
		//뮤지컬
		List<Map<String,Object>> musicalList = kopisService.getList(params);
		
		//2. 결과가 적으면 api 날려보기
		kopisService.sendApi(params);
		//3. 그 결과 디비에 업데이트 치기 
		
		
		
		//keyword
		//movie
		
		//drama
		
		//musical
		//
		//일배치 통해 db에 들어간다 -> db 확인해서 뿌려주기
		return null;
	}

}