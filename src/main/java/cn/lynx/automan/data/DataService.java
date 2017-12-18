package cn.lynx.automan.data;

import cn.lynx.automan.data.entity.Model;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@Component
public class DataService {
    private static final String PERSISTENT_UNIT = "automanDB";
    private EntityManagerFactory emf;

    public DataService() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT);
    }

    public <T extends Model> T save(T t) {
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        T result = t;
        if (t.getId() != 0) {
            result = em.merge(t);
        } else {
            em.persist(t);
        }
        em.getTransaction().commit();
        em.close();

        return result;
    }

    public <T extends Model> boolean remove(Class<T> clazz, int id) {
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        T t = em.find(clazz, id);
        em.remove(t);
        em.getTransaction().commit();
        em.clear();

        return true;
    }

    public <T extends Model> boolean removeAll(Class<T> clazz, Map<String, Object> props) {
        EntityManager em = this.emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> rootEntity = cq.from(clazz);
        cq.select(rootEntity);

        if (props != null) {
            Predicate criteria = cb.conjunction();
            for (String key : props.keySet()) {
                Object val = props.get(key);

                Expression<T> exp = buildExpressionEqual(rootEntity, key);
                criteria = cb.and(criteria, cb.equal(exp, val));
            }

            cq.where(criteria);
        }

        List<T> tlist = em.createQuery(cq).getResultList();

        if (tlist != null) {
            for (T t : tlist) {
                em.remove(t);
            }
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }

    public <T extends Model> T findById(Class<T> clazz, int id) {
        EntityManager em = this.emf.createEntityManager();
        T result = em.find(clazz, id);
        em.close();
        return result;
    }

    public <T extends Model> List<T> findAll(Class<T> clazz) {
        return findAllByPropsOrder(clazz, null, -1, "id", true);
    }

    public <T extends Model> List<T> findAllByPropsOrder(Class<T> clazz, Map<String, Object> props, int topNum, String order, boolean isAsc) {
        EntityManager em = this.emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);

        Root<T> rootEntity = cq.from(clazz);
        cq.select(rootEntity);

        if (props != null) {
            Predicate criteria = cb.conjunction();
            for (String key : props.keySet()) {
                Object val = props.get(key);

                Expression<T> exp = buildExpressionEqual(rootEntity, key);
                criteria = cb.and(criteria, cb.equal(exp, val));
            }

            cq.where(criteria);

            if (isAsc) {
                cq.orderBy(cb.asc(rootEntity.get(order)));
            } else {
                cq.orderBy(cb.desc(rootEntity.get(order)));
            }
        }

        TypedQuery<T> tq = em.createQuery(cq);
        if (topNum > 0) {
            tq.setMaxResults(topNum);
        }
        List<T> tlist = tq.getResultList();
        em.close();
        return tlist;
    }

    public <T extends Model> List<T> findAllByProps(Class<T> clazz, Map<String, Object> props, int topNum) {
        return findAllByPropsOrder(clazz, props, topNum, "id", true);
    }

    private <T extends Model> Expression<T> buildExpressionEqual(Root<T> root, String key) {
        if (key.contains(".")) {
            Path<T> interRoot = root;
            String[] keyChains = key.split("\\.");
            for (int i = 0; i < keyChains.length; i++) {
                interRoot = interRoot.get(keyChains[i]);
            }
            return interRoot;
        } else {
            return root.get(key);
        }
    }
}
