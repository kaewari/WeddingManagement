package com.qltc.repository.impl;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.pojo.DishInBranch;
import com.qltc.repository.DishRepository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DishRepositoryImpl implements DishRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Autowired
    private Environment env;

    @Override
    public List<Dish> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Dish");
        return query.getResultList();
    }

    @Override
    public Dish findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Dish.class, id);
    }

    @Override
    // find({"weddingOnly"="boolean", "keyword"="String"})
    public List<Dish> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder ctrBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Dish> queryBuilder = ctrBuilder.createQuery(Dish.class);
        Root root = queryBuilder.from(Dish.class);

        List<Predicate> predicates = new ArrayList<>();

        String keyword = (String) findArgs.get("keyword");
        if (keyword != null && !keyword.isEmpty()) {
            predicates.add(ctrBuilder.like(root.get("name"), String.format("%%%s%%", keyword.toLowerCase())));
        }

        Boolean weddingOnly = (Boolean) findArgs.get("weddingOnly");
        if (weddingOnly != null) {
            predicates.add(ctrBuilder.equal(root.get("wOnly"), weddingOnly));
        }

        queryBuilder.where(ctrBuilder.and(predicates.toArray(new Predicate[0])));

        Query query = session.createQuery(queryBuilder);

        if (findArgs.get("pageIndex") != null) {
            int pageSize = (Integer) findArgs.get("pageSize");
            int pageIndex = (Integer) findArgs.get("pageIndex");
            query.setFirstResult((pageIndex - 1) * pageSize);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override //ok
    public List<Dish> findAllInBranchId(int branchId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String queryString = "Select d, dib From DishInBranch dib Join Dish d On dib.dish = d Where dib.branch.id = ?1 and d.isAvailable = true Order by dib.id desc";
        Query query = session.createQuery(queryString);
        query.setParameter(1, branchId);
        List<Object[]> list = query.getResultList();
        List<Dish> dishes = new LinkedList<>();
        for (Object[] ob : list) {
            Dish dish = (Dish) ob[0];
            DishInBranch dishInBranch = (DishInBranch) ob[1];
            dish.setIsAvailable(dishInBranch.getIsAvailable());
            dishes.add(dish);
        }
        return dishes;
    }

    @Override
    public boolean addOrUpdateDish(Dish dish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (dish.getId() == null) { //adding
                //add image to cloudinary
                session.save(dish);
            } else { //updating
                session.update(dish);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deactivateDishById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Dish dish = findById(id);
            Set<DishInBranch> allDishAvailability = dish.getDishInBranches();
            allDishAvailability.forEach((DishInBranch dishRecord) -> {
                dishRecord.setIsAvailable(false);
                session.update(dishRecord);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deactivateDish(Dish dish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Set<DishInBranch> allDishAvailability = dish.getDishInBranches();
            allDishAvailability.forEach((DishInBranch dishRecord) -> {
                dishRecord.setIsAvailable(false);
                session.update(dishRecord);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteDishById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Dish dish = findById(id);
            session.delete(dish);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteDish(Dish dish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(dish);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean addDishToBranch(Branch branch, Dish dish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DishInBranch> query = builder.createQuery(DishInBranch.class);
            Root<DishInBranch> root = query.from(DishInBranch.class);
            query.where(builder.and(
                    (builder.equal(root.get("dish"), dish.getId())),
                    (builder.equal(root.get("branch"), branch.getId()))));
            List<DishInBranch> list = session.createQuery(query).setMaxResults(1).getResultList();
            if (!list.isEmpty()) {
                DishInBranch existing = list.get(0);
                existing.setIsAvailable(true);
                session.update(existing);
            } else {
                DishInBranch newOne = new DishInBranch();
                newOne.setBranch(branch);
                newOne.setDish(dish);
                newOne.setIsAvailable(true);
                session.save(newOne);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean removeDishFromBranch(Dish dish, Branch branch) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<DishInBranch> query = builder.createCriteriaDelete(DishInBranch.class);
            Root<DishInBranch> root = query.from(DishInBranch.class);
            query.where(builder.and(
                    (builder.equal(root.get("dish"), dish.getId())),
                    (builder.equal(root.get("branch"), branch.getId()))));
            session.createQuery(query).executeUpdate();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean setAvailableForDishInBranch(Dish dish, Branch branch, boolean isAvailable) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<DishInBranch> query = builder.createCriteriaUpdate(DishInBranch.class);
            Root<DishInBranch> root = query.from(DishInBranch.class);
            query.where(builder.and(
                    (builder.equal(root.get("dish"), dish.getId())),
                    (builder.equal(root.get("branch"), branch.getId()))));
            query.set(root.get("isAvailable"), isAvailable);
            session.createQuery(query).executeUpdate();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

}
