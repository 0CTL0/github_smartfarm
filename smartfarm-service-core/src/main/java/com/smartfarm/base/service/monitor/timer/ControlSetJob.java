package com.smartfarm.base.service.monitor.timer;

import com.smartfarm.base.monitor.core.manager.MonitorControlSetManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("controlSetJob")
public class ControlSetJob {
	protected static Logger log = Logger.getLogger(ControlSetJob.class);
	@Autowired
	private MonitorControlSetManager monitorControlSetManager;

	public void openControl(Map<String, Object> map) {
		try {
			log.info("[ControlSetJob:openControl]open control.");
			monitorControlSetManager.setControlNode((Long) map.get("id"), (short)1);
			log.info("[ControlSetJob:openControl]open control success.");
		} catch (Exception e) {
			log.error("[ControlSetJob:openControl]false to open control.",e);
		}
	}
	
	public void closeControl(Map<String, Object> map) {
		try {
			log.info("[ControlSetJob:closeControl]close control.");
			monitorControlSetManager.setControlNode((Long) map.get("id"), (short)2);
			log.info("[ControlSetJob:closeControl]close control success.");
		} catch (Exception e) {
			log.error("[ControlSetJob:closeControl]false to close control.",e);
		}
	}
}
