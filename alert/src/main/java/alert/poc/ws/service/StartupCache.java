package alert.poc.ws.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import alert.poc.dao.EventDao;
import alert.poc.domain.DrlRules;

@Singleton
@Startup
public class StartupCache {
	@Inject
	private EventDao dao;

	private static Set<DrlRules> drlRules = new HashSet<>();

	@PostConstruct
	private void startup() {
		drlRules = new HashSet<>(dao.makeRules());
	}

	@PreDestroy
	private void shutdown() {
		drlRules =new HashSet<>();
	}

	public static Set<DrlRules> getRulesByEventId(Integer eventId) {
		if (drlRules != null) {
			return drlRules.stream()
					.filter(c -> eventId.equals(c.getEventId()))
					.collect(Collectors.toSet());
		} else
			return null;
	}
	public void refreshRuleCache() {
		drlRules = new HashSet<>(dao.makeRules());
	}
}
