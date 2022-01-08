package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderPojo;

@Repository
public class OrderDao {
	
	@PersistenceContext
	EntityManager em;
	
	public static final String SELECT_ID = "select p from OrderPojo p where id=:id";
	public static final String SELECT_ALL = "select p from OrderPojo p";
	
	public void insert(OrderPojo p) {
		em.persist(p);
	}
	
	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		OrderPojo p = query.getSingleResult();
		return p;
	}
	
	public List<OrderPojo> selectAll(){
		TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
		List<OrderPojo> results = query.getResultList();
		return results;
	}

	public void update(int id,OrderPojo p) {
		return;
	}
	
	private TypedQuery<OrderPojo> getQuery(String q) {
		return em.createQuery(q,OrderPojo.class);
	}	
}
