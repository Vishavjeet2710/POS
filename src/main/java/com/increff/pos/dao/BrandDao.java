package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao {
	@PersistenceContext
	EntityManager em;

	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_CATEGORY_BRAND = "select p from BrandPojo p where category=:category and brand=:brand";
	private static final String SELECT_ALL = "select p from BrandPojo p";

	public void insert(BrandPojo p) {
		em.persist(p);
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		BrandPojo result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			// No need to handle exception here
		}
		return result;
	}
	
	public BrandPojo selectByCategoryBrand(String category, String brand) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_CATEGORY_BRAND);
		query.setParameter("category", category);
		query.setParameter("brand", brand);
		BrandPojo result = null;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// No need to handle exception here
		}
		return result;
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ALL);
		List<BrandPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, BrandPojo p) {
	}

	private TypedQuery<BrandPojo> getQuery(String q) {
		return em.createQuery(q, BrandPojo.class);
	}
}
