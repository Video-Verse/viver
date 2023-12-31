package com.project.viver.repository.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.project.viver.entity.kopis.Musical;
import com.project.viver.repository.DefaultRepository;

public class MusicalRepositoryImpl extends DefaultRepository implements MusicalRepositoryCustom{

	/**
	 * 검색
	 *
	 */
	public Page<Musical> search(Map<String, Object> params, PageRequest pageable) {
		
		Map<String, Object> queryParams = new HashMap<>() ;
	    List<String> where = new ArrayList<>() ;
	    List<String> orders = new ArrayList<>() ;

	    where.add(" t.del_yn = 'N' ") ;
	    String keyword = (String)params.get("KEYWORD") ;
	    if (StringUtils.isNotBlank(keyword))
	    {
	      where.add(" ( t.prfnm LIKE :keyword OR t.prfcast LIKE :keyword  OR t.prfcrew LIKE :keyword ) ") ;
	      queryParams.put("keyword", "%" + keyword + "%") ;
	    }
	    
	    orders.add(" t.prfpdto desc");


		return searchPage(Musical.class, where, queryParams, orders, pageable);
	}

}
