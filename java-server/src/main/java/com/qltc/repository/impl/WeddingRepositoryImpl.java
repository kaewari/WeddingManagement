package com.qltc.repository.impl;

import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.repository.WeddingRepository;
import com.qltc.service.HallService;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WeddingRepositoryImpl implements WeddingRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Autowired
    private HallService hallService;

    @Override
    public List<Wedding> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Wedding");
        return query.getResultList();
    }

    @Override
    public Wedding findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Wedding.class, id);
    }

    @Override
    //find({"completed":"boolean", "onlyDeposit":"boolean", "allPaid":"boolean"});
    public List<Wedding> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Wedding> criteriaQuery = criteriaBuilder.createQuery(Wedding.class);
        Root<Wedding> weddingRoot = criteriaQuery.from(Wedding.class);

        List<Predicate> predicates = new ArrayList<>();

        Boolean isCompleted = (Boolean) findArgs.get("completed");
        if (isCompleted != null) {
            predicates.add(criteriaBuilder.equal(weddingRoot.get("isCompleted"), isCompleted));
        }

        Boolean onlyDeposit = (Boolean) findArgs.get("onlyDeposit");
        if (onlyDeposit != null) {
            predicates.add(criteriaBuilder.isNull(weddingRoot.get("totalLeft")));
        }

        Boolean allPaid = (Boolean) findArgs.get("allPaid");
        if (allPaid != null) {
            predicates.add(criteriaBuilder.isNotNull(weddingRoot.get("totalLeft")));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Wedding> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public boolean addOrUpdate(Wedding wedding) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (wedding.getId() != null) { //updating
                session.update(wedding);
            } else { //adding
                session.save(wedding);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean addPictureToWedding(Wedding wedding, WeddingPicture picture) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            wedding.addWeddingPicture(picture);
            session.save(wedding);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Wedding existing = findById(id);
            if (existing != null) {
                session.delete(existing);
                return false;
            }
            return false;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Wedding wedding) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete((wedding));
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public List<HallPrice> getAvailableHallPrice(Date inDate, int hallId) {
        Set<HallPrice> allHallPrices = hallService.findById(hallId).getPrices();
        if (allHallPrices.isEmpty()) return new ArrayList<>();
        List<HallPrice> unAvailableHallPrices = getUnAvailableHallPrice(inDate, hallId);
        
        List<HallPrice> availableHallPrices = new LinkedList<>();
        allHallPrices.forEach((hallPrice) -> {
            if (!unAvailableHallPrices.contains(hallPrice)) {
                availableHallPrices.add(hallPrice);
            }
        });
        
        return availableHallPrices;
    }
    
    private List<HallPrice> getUnAvailableHallPrice(Date inDate, int hallId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("Select odh.hallPrice From OrderDetailsHall odh Where odh.hallPrice.hall.id = :hallId and odh.order.id In "
                        + "(Select w.order.id From Wedding w Where w.celebrityDate = :inDate)");
        query.setParameter("hallId", hallId);
        query.setParameter("inDate", inDate);
        return query.getResultList();
    }
}
