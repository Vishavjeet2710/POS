package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao{

	private static final String SELECT_ID = "select p from OrderItemPojo p where id=:id";
	private static final String SELECT_ALL = "select p from OrderItemPojo p";
	private static final String SELECT_BY_ORDERID = "select p from OrderItemPojo p where orderId=:orderId";
	private static final String SELECT_BY_ORDERID_BRAND = "select p from OrderItemPojo p,BrandPojo bp,ProductPojo pp where p.productId = pp.id and pp.brand_category = bp.id and p.orderId=:orderId and bp.brand=:brand";
	private static final String SELECT_BY_ORDERID_BRAND_CATEGORY = "select p from OrderItemPojo p,BrandPojo bp,ProductPojo pp where p.productId = pp.id and pp.brand_category = bp.id and p.orderId=:orderId and bp.category=:category and bp.brand=:brand";
	
	public void add(OrderItemPojo p) {
		em().persist(p);
	}
	
	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID,OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public List<OrderItemPojo> selectAll(){
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL,OrderItemPojo.class);
		List<OrderItemPojo> results = query.getResultList();
		return results;
	}
	
	public List<OrderItemPojo> selectByOrderId(int orderId){
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDERID,OrderItemPojo.class);
		query.setParameter("orderId", orderId);
		List<OrderItemPojo> results = query.getResultList();
		return results;
	}
	
	public List<OrderItemPojo> selectByOrderIdBrand(int orderId,String brand){
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDERID_BRAND,OrderItemPojo.class);
		query.setParameter("orderId", orderId);
		query.setParameter("brand", brand);
		List<OrderItemPojo> results = query.getResultList();
		return results;
	}
	
	public List<OrderItemPojo> selectByOrderIdBrandCategory(int orderId,String brand,String category){
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ORDERID_BRAND_CATEGORY,OrderItemPojo.class);
		query.setParameter("orderId", orderId);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		List<OrderItemPojo> results = query.getResultList();
		return results;
	}
	
	public void update(int id,OrderItemPojo p) {
	}
}
