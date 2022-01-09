package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao {
	@PersistenceContext
	EntityManager em;

	private static final String SELECT_ID = "select p from ProductPojo p where id=:id";
	private static final String SELECT_BARCODE = "select p from ProductPojo p where barcode=:barcode";
	private static final String SELECT_ALL = "select p from ProductPojo p";

	public void insert(ProductPojo p) {
		em.persist(p);
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		ProductPojo result = null;
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			// No need to handle exception here
		}
		return result;
	}
	
	public ProductPojo selectByBarcode(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE);
		query.setParameter("barcode", barcode);
		ProductPojo result = null;
		try {
			result = query.getSingleResult();
		} catch (Exception e) {
			// No need to handle exception here
		}
		return result;
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ALL);
		List<ProductPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, ProductPojo p) {
		return;
	}

	private TypedQuery<ProductPojo> getQuery(String q) {
		return em.createQuery(q, ProductPojo.class);
	}
}
