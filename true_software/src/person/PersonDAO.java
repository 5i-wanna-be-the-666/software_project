package person;

import booking_information.BaseHibernateDAO;
import dao.HibernateSessionFactory;

import java.util.List;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Person entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see person.Person
 * @author MyEclipse Persistence Tools
 */
public class PersonDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(PersonDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String SEX = "sex";
	public static final String _EMAIL = "EMail";

	public void save(Person transientInstance) { 
Session session = null;
		
		session = HibernateSessionFactory.getSession();
		session.beginTransaction();	//开启事物
		log.debug("saving Person instance");
		try {
			getSession().save(transientInstance);
			session.save(transientInstance);
			session.getTransaction().commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Person persistentInstance) {
Session session = null;
		
		session = HibernateSessionFactory.getSession();
		session.beginTransaction();	//开启事物
		log.debug("deleting Person instance");
		try {
			getSession().delete(persistentInstance);
			session.delete(persistentInstance);
			session.getTransaction().commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Person findById(java.lang.String id) {
		log.debug("getting Person instance with id: " + id);
		try {
			Person instance = (Person) getSession().get("person.Person", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Person instance) {
		log.debug("finding Person instance by example");
		try {
			List results = getSession().createCriteria("person.Person").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Person instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Person as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByEMail(Object EMail) {
		return findByProperty(_EMAIL, EMail);
	}

	public List findAll() {
		log.debug("finding all Person instances");
		try {
			String queryString = "from Person";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Person merge(Person detachedInstance) {
		log.debug("merging Person instance");
		try {
			Person result = (Person) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Person instance) {
		log.debug("attaching dirty Person instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Person instance) {
		log.debug("attaching clean Person instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}