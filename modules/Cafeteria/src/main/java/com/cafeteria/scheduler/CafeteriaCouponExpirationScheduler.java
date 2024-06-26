package com.cafeteria.scheduler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.plng.common.schema.service.CouponStatusLocalService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

@Component(
	    immediate = true,
	    property = {
	        "cron.expression=0 */1 * * * ?" 
	    },
	    service = CafeteriaCouponExpirationScheduler.class
	)
public class CafeteriaCouponExpirationScheduler extends BaseMessageListener {
	 private Log _log = LogFactoryUtil.getLog(CafeteriaCouponExpirationScheduler.class);
	 private boolean initialized = false;
	 private Trigger trigger;
	@Override
	protected void doReceive(Message message) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        _couponStatusLocalService.couponsExpiration();
	}
	@Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
        try {
            String cronExpression = GetterUtil.getString(properties.get("cron.expression"), "cron.expression");
            String listenerClass = getClass().getName();
            trigger = _triggerFactory.createTrigger(listenerClass, listenerClass, new Date(), null, cronExpression);

            SchedulerEngineHelperUtil.schedule(trigger, StorageType.PERSISTED, null, DestinationNames.SCHEDULER_DISPATCH, Message.class.getName());

            if (!initialized) {
                destination = messageBus.getDestination(DestinationNames.SCHEDULER_DISPATCH);
                destination.register(this);
                initialized = true;
            }
        } catch (Exception e) {
            _log.debug(e.getMessage());
        }
    }

    @Deactivate
    protected void deactivate() {
        try {
            if (initialized && trigger != null) {
                SchedulerEngineHelperUtil.unschedule(trigger.getJobName(), trigger.getGroupName(), StorageType.PERSISTED);
                destination.unregister(this);
                initialized = false;
                SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            }
        } catch (SchedulerException se) {
        	 _log.debug(se.getMessage());
        }
    }
    @Reference private MessageBus messageBus;

    @Reference private TriggerFactory _triggerFactory;

    private Destination destination;
	@Reference private CouponStatusLocalService _couponStatusLocalService;
}