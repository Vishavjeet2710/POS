package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.InventoryPojo;

@Repository
public class InventoryDao {
	@PersistenceContext
	EntityManager em;
	
	private static String select_id="select p from InventoryPojo p where id=:id";
	private static String select_all="select p from InventoryPojo p";

	public void insert(InventoryPojo p) {
		em.persist(p);
	}
	
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		InventoryPojo result = query.getSingleResult();
		return result;
	}
	
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(select_all);
		List<InventoryPojo> results = query.getResultList();
		return results;
	}
	
	public void update(int id,InventoryPojo p) {
		return;
	}

	private TypedQuery<InventoryPojo> getQuery(String q) {
		return em.createQuery(q,InventoryPojo.class);
	}
}
