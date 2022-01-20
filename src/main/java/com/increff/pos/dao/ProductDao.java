package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao{

	private static final String SELECT_ID = "select p from ProductPojo p where id=:id";
	private static final String SELECT_BARCODE = "select p from ProductPojo p where barcode=:barcode";
	private static final String SELECT_ALL = "select p from ProductPojo p";

	public void insert(ProductPojo p) {
		em().persist(p);
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ID,ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	public ProductPojo selectByBarcode(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE,ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}

	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ALL,ProductPojo.class);
		List<ProductPojo> results = query.getResultList();
		return results;
	}

	public void update(int id, ProductPojo p) {
		return;
	}
}
