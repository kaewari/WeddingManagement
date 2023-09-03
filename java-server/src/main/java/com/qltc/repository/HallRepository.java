package com.qltc.repositories;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Hall;
import java.util.List;
import java.util.Map;

public interface HallRepository {
    public List<Hall> findAll();
    public Hall findById(int id);
    public List<Hall> find(Map<String, Object> findArgs);
    public List<Hall> findAllInBranch(Branch branch);
    public boolean addOrUpdateHall(Hall hall);
    public boolean deactivateOrActivateHallById(int id, boolean isActive);
    public boolean deactivateOrActivateHall(Hall hall, boolean isActive);
    public boolean deleteHallById(int id);
    public boolean deleteHall(Hall hall);
}
