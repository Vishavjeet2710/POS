package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao{
	@PersistenceContext
	EntityManager em;
	
	private static final String SELECT_ID="select p from InventoryPojo p where id=:id";
	private static final String SELECT_ALL="select p from InventoryPojo p";

	public void insert(InventoryPojo p) {
		em.persist(p);
	}
	
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(SELECT_ID,InventoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL,InventoryPojo.class);
		List<InventoryPojo> results = query.getResultList();
		return results;
	}
	
	public void update(int id,InventoryPojo p) {
		return;
	}
}
