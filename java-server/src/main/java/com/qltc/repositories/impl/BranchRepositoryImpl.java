package com.qltc.repositories.impl;

import com.qltc.pojo.Branch;
import com.qltc.repositories.BranchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
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
public class BranchRepositoryImpl implements BranchRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    

    @Override
    public List<Branch> findAll(boolean isActive) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Branch b Where b.isActive = :isActive");
        query.setParameter("isActive", isActive);
        return query.getResultList();
    }

    @Override
    public Branch findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Branch.class, id);
    }

    @Override
    // find({"province"="string", "district"="string", "ward"="string", "quarter":"string"
    // "houseNumber="String", "inactive":"boolean"})
    public List<Branch> find(Map<String, Object> findArgs) { 
        Session session = sessionFactory.getObject().getCurrentSession();
        if (session != null) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery query = builder.createQuery();
            Root<Branch> root = query.from(Branch.class);
            
            List<Predicate> predicates = new ArrayList<>();
            
            String province = (String) findArgs.get("province");
            if (province != null && !province.isEmpty()) {
                predicates.add(builder.like(root.get("province"), String.format("%%%s%%", province)));
            }
            String district = (String) findArgs.get("district");
            if (district != null && !district.isEmpty()) {
                predicates.add(builder.like(root.get("district"), String.format("%%%s%%", district)));
            }
            String ward = (String) findArgs.get("ward");
            if (ward != null && !ward.isEmpty()) {
                predicates.add(builder.like(root.get("ward"), String.format("%%%s%%", ward)));
            }
            String quarter = (String) findArgs.get("quarter");
            if (quarter != null && !quarter.isEmpty()) {
                predicates.add(builder.like(root.get("quarter"), String.format("%%%s%%", quarter)));
            }
            String houseNumber = (String) findArgs.get("houseNumber");
            if (houseNumber != null && !houseNumber.isEmpty()) {
                predicates.add(builder.like(root.get("houseNumber"), String.format("%%%s%%", houseNumber)));
            }
            Boolean inactive = (Boolean) findArgs.get("inactive");
            if (inactive != null) {
                predicates.add(builder.equal(root.get("isActive"), inactive));
            }
            
            query.select(root).where(predicates.toArray(new Predicate[] {}));
            
            Query q = session.createQuery(query);
            
            if (findArgs.get("pageIndex") != null && findArgs.get("pageSize") != null) {
                int pageIndex = (Integer) findArgs.get("pageIndex");
                int pageSize = (Integer) findArgs.get("pageSize");
                q.setFirstResult(pageSize * (pageIndex - 1));
                q.setMaxResults(pageSize);
            }
            return q.getResultList();
        }
        return null;
    } 

    @Override
    public boolean addOrUpdateBranch(Branch branch) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (branch.getId() == null) { //action adding branch
                session.save(branch);
            } else { //action updating branch
                session.update(branch);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deactivateOrActivateBranchById(int id, boolean isActive) {
        Branch branch = findById(id);
        if (branch != null) {
            Session session = sessionFactory.getObject().getCurrentSession();
            try {
                branch.setIsActive(isActive);
                session.update(branch);
                return true;
            } catch (HibernateException e) {}
        }
        return false;
    }

    @Override
    public boolean deactivateOrActivateBranch(Branch branch, boolean isActive) {
        if (branch != null) {
            Session session = sessionFactory.getObject().getCurrentSession();
            try {
                branch.setIsActive(isActive);
                session.update(branch);
                return true;
            } catch (HibernateException e) {}
        }
        return false;
    }
        
    @Override
    public boolean deleteBranchById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Query query = session.createQuery("Delete Branch b Where b.id = ?1");
            query.setParameter(1, id);
            query.executeUpdate();
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteBranch(Branch branch) {
        return deleteBranchById(branch.getId());
    }
        
}
