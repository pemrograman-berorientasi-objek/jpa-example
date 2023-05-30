package pbo.p00;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import pbo.p00.model.Contact;
import pbo.p00.model.Group;

/**
 * Main class
 *
 */
public class App {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory("contacts_pu");
        entityManager = factory.createEntityManager();

        // display
        displayAllContacts();
        displayAllGroups();

        // delete
        cleanTables();

        // insert
        seedTables();

        displayAllContacts();
        displayAllGroups();

        // select
        displayContactsWithNameContains("iro");

        displayContactsWithEmailContains("heroes.com");

        entityManager.close();
        System.out.println("Hello guys!");
    }

    private static void displayAllContacts() {
        String jpql = "SELECT c FROM Contact c ORDER BY c.name";
        List<Contact> contacts = entityManager.createQuery(jpql, Contact.class)
                .getResultList();

        System.out.println("displayAllContacts--");
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    private static void displayAllGroups() {
        String jpql = "SELECT g FROM Group g ORDER BY g.id";
        List<Group> groups = entityManager.createQuery(jpql, Group.class)
                .getResultList();

        System.out.println("displayAllGroups--");
        for (Group g : groups) {
            System.out.println(g);
            
            Set<Contact> contacts = g.getContacts();
            for (Contact c : contacts) {
                System.out.println(c);
            }
        }
    }

    private static void seedTables() {
        System.out.println("seedTables--");

        entityManager.getTransaction().begin();

        Contact cWiro = new Contact("wiro@sableng.com", "Wiro Sableng", "021-212-212");
        Contact cJaka = new Contact("jaka@sembung.com", "Jaka Sembung", "021-333-444");
        Contact cMlkm = new Contact("milkyman@heroes.com", "Milkyman", "021-500-123");
        Contact cSpmn = new Contact("supraman@flying-heroes.com", "Supraman", "0361-610-623");

        Group gFaml = new Group(1, "Family", Set.of(cMlkm));
        Group gFrnd = new Group(2, "Friend", Set.of(cWiro, cJaka, cSpmn));
        Group gWork = new Group(3, "Work", Set.of(cMlkm));

        entityManager.persist(cWiro);
        entityManager.persist(cJaka);
        entityManager.persist(cMlkm);
        entityManager.persist(cSpmn);

        entityManager.persist(gFaml);
        entityManager.persist(gFrnd);
        entityManager.persist(gWork);

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    private static void cleanTables() {
        String deleteContactJpql = "DELETE FROM Contact c";
        String deleteGroupJpql = "DELETE FROM Group g";

        entityManager.getTransaction().begin();

        int deletedContacts = entityManager.createQuery(deleteContactJpql).executeUpdate();
        int deletedGroups = entityManager.createQuery(deleteGroupJpql).executeUpdate();

        entityManager.flush();
        entityManager.getTransaction().commit();

        System.out.println("cleanTables: " + deletedContacts + " contacts");
        System.out.println("cleanTables: " + deletedGroups + " groups");
    }

    private static void displayContactsWithNameContains(String namePartial) {
        String jpql = "SELECT c FROM Contact c WHERE c.name LIKE :nameCriteria ORDER BY c.name";
        TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
        query.setParameter("nameCriteria", "%" + namePartial + "%");
        List<Contact> contacts = query.getResultList();

        System.out.println("displayContactsWithNameContains--");
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    private static void displayContactsWithEmailContains(String emailPartial) {
        String jpql = "SELECT c FROM Contact c WHERE c.email LIKE :emailCriteria ORDER BY c.name";
        TypedQuery<Contact> query = entityManager.createQuery(jpql, Contact.class);
        query.setParameter("emailCriteria", "%" + emailPartial + "%");
        List<Contact> contacts = query.getResultList();

        System.out.println("displayContactsWithEmailContains--");
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }
}
