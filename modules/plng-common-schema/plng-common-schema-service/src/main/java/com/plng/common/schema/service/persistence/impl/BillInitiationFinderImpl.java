package com.plng.common.schema.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.plng.common.schema.model.BillInitiation;
import com.plng.common.schema.model.impl.BillInitiationImpl;
import com.plng.common.schema.service.persistence.BillInitiationFinder;

import java.math.BigInteger;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


@Component(service = BillInitiationFinder.class)
public class BillInitiationFinderImpl extends BillInitiationFinderBaseImpl implements BillInitiationFinder{
	@SuppressWarnings("unchecked")
	public List<BillInitiation> getAllClosedBill(String currentDate) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), ALL_CLOSED_BILL);
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.addEntity("BillInitiation", BillInitiationImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(query);
			qPos.add(currentDate);
			
			return (List<BillInitiation>) query.list(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public BillInitiation getFinancePending(long assignedUser, String billInitiationUniqueId) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), FINANCE_PENDING);
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.addEntity("BillInitiation", BillInitiationImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(query);
			qPos.add(assignedUser);
			qPos.add(billInitiationUniqueId);
			
			 return (BillInitiation) query.uniqueResult(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}
	
	public int countAllAcceptBills(long assignedUser) {
		Session session = null;
		try {
			session = openSession();
			
			String sql = customSQL.get(getClass(), COUNT_ALL_ACCEPT_BILLS);
			
			Query query = session.createSQLQuery(sql);
			query.setCacheable(false);
			QueryPos queryPos = QueryPos.getInstance(query);
			queryPos.add(assignedUser);
			BigInteger count = (BigInteger) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	public List<BillInitiation> getAllAcceptBill(long assignedUser) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), ALL_ACCEPT_BILLS);
			
			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.addEntity("BillInitiation", BillInitiationImpl.class);
			
			QueryPos qPos = QueryPos.getInstance(query);
			qPos.add(assignedUser);
			
			return (List<BillInitiation>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<BillInitiation> getAllSentBill(long sentuserId) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), ALL_SENT_BILLS);

			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.addEntity("BillInitiation", BillInitiationImpl.class);

			QueryPos qPos = QueryPos.getInstance(query);
			qPos.add(sentuserId);

			return (List<BillInitiation>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}
	public int countAllPendingBills(long currentlyWith) {
		Session session = null;
		try {
			session = openSession();
			
			String sql = customSQL.get(getClass(), COUNT_PENDING_BILLS);
			
			Query query = session.createSQLQuery(sql);
			query.setCacheable(false);
			QueryPos queryPos = QueryPos.getInstance(query);
			queryPos.add(currentlyWith);
			BigInteger count = (BigInteger) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return 0;
	}
	public static final String ALL_CLOSED_BILL = BillInitiationFinder.class.getName() + ".allClosedBill";
	public static final String FINANCE_PENDING = BillInitiationFinder.class.getName() + ".financePending";
	public static final String ALL_PENDING_BILLS_COUNT = BillInitiationFinder.class.getName() + ".allPendingBillsCount";
	public static final String ALL_ACCEPT_BILLS = BillInitiationFinder.class.getName() + ".allAcceptBills";
	public static final String COUNT_ALL_ACCEPT_BILLS = BillInitiationFinder.class.getName() + ".countAllAcceptBills";
	public static final String ALL_SENT_BILLS = BillInitiationFinder.class.getName() + ".getAllSentBill";
	public static final String COUNT_PENDING_BILLS = BillInitiationFinder.class.getName() + ".countAllPendingBills";
	@Reference
	private CustomSQL customSQL;
}
