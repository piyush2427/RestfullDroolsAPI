package alert.poc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import alert.poc.domain.DrlRules;
import alert.poc.domain.Event;

public class EventDao {
	@PersistenceUnit(unitName = "default")
	public EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public EntityManager getEntityManager() {

		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("default");
		}

		return entityManagerFactory.createEntityManager();
	}
	
	public void save(Event entity) {
		EntityManager em = getEntityManager();
		if (em != null) {
			em.merge(entity);
			em.close();
		} else {
		//	log.error("save:could not obtain EntityManager for persistentClass=" + persistentClass.getName());
		}
	}
	
	public Event getEventByEventId(Integer eventId) {
		EntityManager em = getEntityManager();
		Event event = null;
		if (em != null) {
			Query query = em.createQuery("select e FROM Event e where e.eventId=:eventId");
			query.setParameter("eventId", eventId);
			try {
				event = (Event) query.getSingleResult();
			} catch (Throwable t) {

			}
			em.close();
		} else {
			
		}
		return event;

	}
	public List<Event> getAllEvents() {
		EntityManager em = getEntityManager();
		List<Event> events = null;
		if (em != null) {
			Query query = em.createQuery("select e FROM Event e");
			try {
				events = (List<Event>) query.getResultList();
			} catch (Throwable t) {

			}
			em.close();
		} else {
			
		}
		return events;

	}
	
	public List<DrlRules> makeRules() {
		List<DrlRules> drlRules = new ArrayList<DrlRules>();
		EntityManager em = getEntityManager();
		if (em != null) {
			Query query = em.createQuery("select e FROM DrlRules e");
			try {
				drlRules = (List<DrlRules>) query.getResultList();
			} catch (Throwable t) {

			}
			em.close();
		} else {
			// log.error("save:could not obtain EntityManager for persistentClass=" +
			// persistentClass.getName());
		}
		return drlRules;

	}
}
