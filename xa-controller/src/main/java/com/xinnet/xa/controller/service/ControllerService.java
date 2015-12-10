package com.xinnet.xa.controller.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.controller.common.ControllerConstant;
import com.xinnet.xa.controller.dao.CompConstantsDao;
import com.xinnet.xa.controller.dao.ComponentDao;
import com.xinnet.xa.controller.model.CompConstants;
import com.xinnet.xa.controller.model.Component;
import com.xinnet.xa.controller.model.IpAndPortTransferId;
import com.xinnet.xa.core.vo.CompData;

@Service
public class ControllerService {
	@Autowired
	private ComponentDao componentDao;
	@Autowired
	private CompConstantsDao compConstantsDao;

	public void saveComponent(CompData data) {
		IpAndPortTransferId id = new IpAndPortTransferId();
		id.setIp(data.getIp());
		id.setPort(data.getPort());
		Component component = componentDao.findOne(id);
		if (component == null) {
			component = new Component();
			component.setId(id);
			component.setType(data.getType());
			component.setState(ControllerConstant.ComponentState.NORMAL
						.getValue());
			componentDao.save(component);
		} else {
			if (component.getState().intValue() == ControllerConstant.ComponentState.ERROR
					.getValue()) {
				component.setState(ControllerConstant.ComponentState.NORMAL
						.getValue());
				componentDao.save(component);
			}
		}

	}

	public Map<IpAndPortTransferId, Component> getAllComponent() {
		Iterable<Component> components = componentDao.findAll();
		Map<IpAndPortTransferId, Component> componentMap = new HashMap<IpAndPortTransferId, Component>();
		for (Component component : components) {
			componentMap.put(component.getId(), component);
		}
		return componentMap;
	}

	public CompConstants getConstants(CompData data) {
		IpAndPortTransferId id = new IpAndPortTransferId();
		id.setIp(data.getIp());
		id.setPort(data.getPort());
		CompConstants comstant = compConstantsDao.findOne(id);
		if (comstant == null) {
			id.setIp("default");
			id.setPort(0);
			comstant = compConstantsDao.findOne(id);
		}
		return comstant;
	}

}
