package alert.poc.ws.service;

import java.io.StringReader;
import java.util.List;
//import java.util.function.Predicate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
//import javax.ws.rs

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import alert.poc.app.service.EventService;
import alert.poc.domain.Event;

@Path("/message")
public class EventListenerService {

	@Inject
	private EventService service;

	@Inject
	private StartupCache startupCache;

	@GET
	@Path("/refreshRuleCache")
	@Produces("application/json")
	public void refreshRuleCache() {
		startupCache.refreshRuleCache();
	}

	@GET
	@Path("/fireRuleByEvent/{eventId}")
	@Produces("application/json")
	public Event getEvents(@PathParam("eventId") Integer eventId) {

		KieSession kieSession = null;
		try {
			Event event = service.getEventByEventId(eventId);
			String rules = service.makeRulesByEventId(eventId);

			KieServices kieServices = KieServices.Factory.get();
			KieFileSystem kfs = kieServices.newKieFileSystem();
			kfs.write("src/main/resources/rulesfromdb.drl",
					kieServices.getResources().newReaderResource(new StringReader(rules)));
			KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
			Results results = kieBuilder.getResults();
			if (results.hasMessages(Message.Level.ERROR)) {
				System.out.println(results.getMessages());
				throw new IllegalStateException("### errors ###");
			}

			KieContainer kieContainer = kieServices.newKieContainer(kieBuilder.getKieModule().getReleaseId());
			kieSession = kieContainer.newKieSession();

			// insert fact
			kieSession.insert(event);

			// execute rules
			kieSession.fireAllRules();

			// update event with result
			service.save(event);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			kieSession.dispose();
		}
		return service.getEventByEventId(eventId);
	}

	public EventService getService() {
		return service;
	}

	public void setService(EventService service) {
		this.service = service;
	}

}
