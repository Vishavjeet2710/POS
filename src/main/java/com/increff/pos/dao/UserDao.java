package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.UserPojo;

@Repository
public class UserDao {
	
	@PersistenceContext
	EntityManager em;
	
	private static final String SELECT_ID = "select p from UserPojo p where id=:id";
	private static final String SELECT_EMAIL = "select p from UserPojo p where email=:email";
	private static final String SELECT_ALL = "select p from UserPojo p";
	private static final String DELETE_ID = "delete from UserPojo p where id=:id";
	
	public void add(UserPojo p) {
		em.persist(p);
	}
	
	public UserPojo select(int id) {
		TypedQuery<UserPojo> query = getQuery(SELECT_ID);
		query.setParameter("id", id);
		UserPojo p = null;
		try {
			p = query.getSingleResult();
		} catch (NoResultException e) {
			// No need to handle here
		}
		return p;
	}

	public UserPojo selectEmail(String email){
		TypedQuery<UserPojo> query = getQuery(SELECT_EMAIL);
		query.setParameter("email", email);
		UserPojo p = null;
		try {
			p = query.getSingleResult();
		} catch (NoResultException e) {
			// No need to handle here
		}
		return p;
	}
	
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	
	public List<UserPojo> selectAll(){
		TypedQuery<UserPojo> query = getQuery(SELECT_ALL);
		return query.getResultList();
	}
	
	public void update(int id,UserPojo p) {
		
	}
	
	private TypedQuery<UserPojo> getQuery(String q) {
		return em.createQuery(q,UserPojo.class);
	}
}
