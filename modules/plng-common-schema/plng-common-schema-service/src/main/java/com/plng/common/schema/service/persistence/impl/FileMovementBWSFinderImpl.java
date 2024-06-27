package com.plng.common.schema.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.FileMovementBWS;
import com.plng.common.schema.model.impl.FileMovementBWSImpl;
import com.plng.common.schema.service.persistence.FileMovementBWSFinder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = FileMovementBWSFinder.class)
public class FileMovementBWSFinderImpl extends FileMovementBWSFinderBaseImpl implements FileMovementBWSFinder {
	private static final Log _log = LogFactoryUtil.getLog(FileMovementBWSFinderImpl.class);

	
	public int countAllSentBill(String billInitiationUniqueId, String sentBy) {
		Session session = null;
		try {
			session = openSession();
			
			String sql = customSQL.get(getClass(), FIND_BY_SENTBY);
			
			Query query = session.createSQLQuery(sql);
			query.setCacheable(false);
			QueryPos queryPos = QueryPos.getInstance(query);
			queryPos.add(billInitiationUniqueId);
			queryPos.add(sentBy);
			BigInteger count = (BigInteger) query.uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return 0;
	}

	public int countByAckOnDateAndSentToEmpIdAndPrefix(Date ackOnDateStart, Date ackOnDateEnd,
			String billInitiationUniqueIdPrefix) {
		Session session = null;
		try {
			_log.info("ackOnDateStart:"+ackOnDateStart+"ackOnDateEnd:"+ackOnDateEnd+"billInitiationUniqueIdPrefix:"+billInitiationUniqueIdPrefix);
			session = openSession();

			String sql = customSQL.get(getClass(), COUNT_BY_ACKONDATE);

			Query query = session.createSQLQuery(sql);
			query.setCacheable(false);
			QueryPos queryPos = QueryPos.getInstance(query);
			queryPos.add(ackOnDateStart);
			queryPos.add(ackOnDateEnd);
			queryPos.add(billInitiationUniqueIdPrefix);
			BigInteger count = (BigInteger) query.uniqueResult();
			_log.info("count:"+count);
			 return (count != null) ? count.intValue() : 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<FileMovementBWS> getAllFinanceBill(Date startDate, Date endDate, String prefix) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), GET_ALL_FILEMOVEMENTBWS);

			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.addEntity("FileMovementBWS", FileMovementBWSImpl.class);

			QueryPos qPos = QueryPos.getInstance(query);
			qPos.add(startDate);
			qPos.add(endDate);
			qPos.add(prefix);

			return (List<FileMovementBWS>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return new ArrayList<>();
	}

	public int getFinalStatus(String billInitiationUniqueId) {
		Session session = null;
		try {
			session = openSession();
			String sql = customSQL.get(getClass(), GET_FINAL_STATUS);

			SQLQuery query = session.createSQLQuery(sql);
			query.setCacheable(false);
			query.setLong(0, Long.parseLong(billInitiationUniqueId));

			Integer actionTaken = (Integer) query.uniqueResult();
			return (actionTaken != null) ? actionTaken : 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return 0;
	}

	public static final String FIND_BY_SENTBY = FileMovementBWSFinder.class.getName() + ".countAllSentBill";
	public static final String COUNT_BY_ACKONDATE = FileMovementBWSFinder.class.getName()
			+ ".getAllInvoiceRecievedFinanceBill";

	public static final String GET_ALL_FILEMOVEMENTBWS = FileMovementBWSFinder.class.getName() + ".getAllFinanceBill";

	public static final String GET_FINAL_STATUS  = FileMovementBWSFinder.class.getName() + ".getFinalStatus";
	
	@Reference
	private CustomSQL customSQL;
}
