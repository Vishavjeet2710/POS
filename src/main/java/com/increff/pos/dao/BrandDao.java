package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
public class BrandDao {
	@PersistenceContext
	EntityManager em;

	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_ALL = "select p from BrandPojo p";

	public void insert(BrandPojo p) {
		em.persist(p);
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		BrandPojo result = query.getSingleResult();
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
