package com.myfood.daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myfood.dao.RestaurantMenuDao;
import com.myfood.model.MenuItem;
import com.myfood.model.Restaurant;

@Repository("restaurantMenuDao")
public class RestaurantMenuDaoImpl implements RestaurantMenuDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		Session session;
		try{
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		return session;
    }

	@Transactional
	public List<MenuItem> getMenuByRestaurant(int restaurantId) {
		List<MenuItem> items = getSession().createQuery("FROM MenuItem m WHERE m.restaurantId=:id and m.flag=1").
				setParameter("id", restaurantId).list();
		return items;
	}
	
	public MenuItem getMenuByItemId(int itemId) {
		MenuItem menuItem = (MenuItem)getSession().get(MenuItem.class, itemId);
		return menuItem;
	}
	
	//sujanth
	public int postMenuItemData(MenuItem menuitem){
		//System.out.println("Menu Item before saving:"+menuitem);
		Session session = getSession();
		try{
			Transaction tx = session.beginTransaction();
			session.save(menuitem);
			tx.commit();	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		System.out.println("In DAO-add-menuItem:"+menuitem);
		return menuitem.getItemId();
	}
	
	//sujanth
	public void removeMenuItem(int id) throws Exception{
		System.out.println("in dao impl--"+id);
		Session session = getSession();
		Transaction tx = getSession().getTransaction();
		MenuItem mitem = new MenuItem();
		mitem.setRestaurantId(id);
		try{
			tx = session.beginTransaction();
			Query query = (Query) session.createQuery("update MenuItem set Flag=:flag where ItemId=:id");
			query.setParameter("flag", 0);
			query.setParameter("id", id);
			query.executeUpdate();
			tx.commit();
			session.update(mitem);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
	
	//sujanth
	public int getLastItemId(){
		System.out.println("In DAO");
		Query query = getSession().createQuery("FROM MenuItem order by ItemId desc");
		query.setMaxResults(1);
		MenuItem mitem = (MenuItem)query.uniqueResult();
		System.out.println("last menu item--"+mitem);
		if(mitem == null)
			return 0;
		else
			return mitem.getItemId();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public List<MenuItem> getMenuItems(int restaurantId){
		
		List<Object[]> resMenuItems1=null;
		List<MenuItem> resMenuItems=new ArrayList<MenuItem>();
		try {
			resMenuItems1 =getSession().createQuery("select itemId,itemName,category,calories,cost FROM MenuItem where restaurantId=:Id and flag=1").setParameter("Id", restaurantId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Iterator iterator = resMenuItems1.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			MenuItem mi=new MenuItem();
			mi.setItemId((Integer)objects[0]);
			mi.setItemName((String)objects[1]);
			mi.setCategory((String)objects[2]);
			mi.setCalories((Integer)objects[3]);
			mi.setCost((Double)objects[4]);
			
			resMenuItems.add(mi);
		}
		return resMenuItems;
	}
	
}
