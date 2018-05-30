package alert.poc.app.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import alert.poc.dao.EventDao;
import alert.poc.domain.DrlRules;
import alert.poc.domain.Event;
import alert.poc.ws.service.StartupCache;

@Transactional
public class EventService {
	@Inject
	private EventDao dao;
	public void save(Event event) {
		dao.save(event);
	}
	public List<Event> getAllEvents(){
		return dao.getAllEvents();
	}
	
	public Event getEventByEventId(Integer eventId){
		return dao.getEventByEventId(eventId);
	}
	
	public String makeRulesByEventId(Integer eventId) {
		Set<DrlRules> rules = StartupCache.getRulesByEventId(eventId);
		Set<String> imports = new HashSet<String>();
		HashMap<Integer, String> ruleMap = new HashMap<Integer, String>();
		HashMap<Integer, String> whenMap = new HashMap<Integer, String>();
		HashMap<Integer, String> thenMap = new HashMap<Integer, String>();
		for(DrlRules rule : rules){
			if(null!=rule.getPackImport() && !rule.getPackImport().isEmpty())
				imports.add(rule.getPackImport());
			
			ruleMap.put(rule.getRuleId(), rule.getRuleSt());
			whenMap.put(rule.getRuleId(), rule.getWhenSt());
			thenMap.put(rule.getRuleId(), rule.getThenSt());
		}
		StringBuilder  builder = new StringBuilder();
		for(String imp : imports)
		{
				builder.append(imp);builder.append("\n");
		}
		
		for(DrlRules rule : rules){
			builder.append("rule ");
			builder.append("\"");builder.append(ruleMap.get(rule.getRuleId()));builder.append("\"");builder.append("\n");
			builder.append("when");builder.append("\n");builder.append(whenMap.get(rule.getRuleId()));builder.append("\n");
			builder.append("then");builder.append("\n");builder.append(thenMap.get(rule.getRuleId()));builder.append("\n");
			builder.append("end");builder.append("\n");
		}
		return builder.toString();
	}
}
