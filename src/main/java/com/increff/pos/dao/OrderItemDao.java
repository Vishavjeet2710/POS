package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderItemPojo;

@Repository
public class OrderItemDao {
	
	@PersistenceContext
	EntityManager em;

	private static final String SELECT_ID = "select p from OrderItemPojo p where id=:id";
	private static final String SELECT_ALL = "select p from OrderItemPojo p";
	
	public void add(OrderItemPojo p) {
		em.persist(p);
	}
	
	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		OrderItemPojo p = null;
		try {
			p = query.getSingleResult();
		} catch (NoResultException e) {
			// No need to Handle here
		}		
		return p;
	}

	public List<OrderItemPojo> selectAll(){
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL);
		List<OrderItemPojo> results = query.getResultList();
		return results;
	}
	
	public void update(int id,OrderItemPojo p) {
	}
	
	private TypedQuery<OrderItemPojo> getQuery(String q) {
		return em.createQuery(q,OrderItemPojo.class);
	}
}
