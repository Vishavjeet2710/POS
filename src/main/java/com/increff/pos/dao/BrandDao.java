package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao{
	
	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_CATEGORY_BRAND = "select p from BrandPojo p where category=:category and brand=:brand";
	private static final String SELECT_ALL = "select p from BrandPojo p";

	public void insert(BrandPojo p) {
		em().persist(p);
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID,BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	public BrandPojo selectByCategoryBrand(String category, String brand) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_CATEGORY_BRAND,BrandPojo.class);
		query.setParameter("category", category);
		query.setParameter("brand", brand);
		return getSingle(query);
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ALL,BrandPojo.class);
		List<BrandPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, BrandPojo p) {
	}
}
