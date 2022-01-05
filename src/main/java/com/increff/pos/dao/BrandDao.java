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
	EntityManager emBrand;

	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_all = "select p from BrandPojo p";

	public void insert(BrandPojo p) {
		emBrand.persist(p);
	}

	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		BrandPojo result = query.getSingleResult();
		return result;
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all);
		List<BrandPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, BrandPojo p) {
	}

	private TypedQuery<BrandPojo> getQuery(String q) {
		return emBrand.createQuery(q, BrandPojo.class);
	}
}
