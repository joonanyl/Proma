package r8.model.dao;

import r8.model.Event;


import javax.persistence.EntityManager;

public class EventDAO {
    private EntityManager entityManager;

    public EventDAO() { this.entityManager = DAOUtil.getEntityManager(); }

    public void addEvent(Event event) {
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.getTransaction().commit();
    }

    public Event getEvent(int eventId) {
        Event event = entityManager.getReference(Event.class, eventId);
        entityManager.detach(event);
        return event;
    }

    public void update(Event event) {
        entityManager.getTransaction().begin();
        entityManager.merge(event);
        entityManager.getTransaction().commit();
    }

    public void remove(Event event) {
        entityManager.getTransaction().begin();
        entityManager.remove(event);
        entityManager.getTransaction().commit();
    }
}
