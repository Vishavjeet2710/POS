package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao {
	@PersistenceContext
	EntityManager emProduct;

	private static String select_id = "select p from ProductPojo p where id=:id";
	private static String select_all = "select p from ProductPojo p";

	public void insert(ProductPojo p) {
		emProduct.persist(p);
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		ProductPojo result = query.getSingleResult();
		return result;
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(select_all);
		List<ProductPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, ProductPojo p) {
		return;
	}

	private TypedQuery<ProductPojo> getQuery(String q) {
		return emProduct.createQuery(q, ProductPojo.class);
	}
}
