package com.nisum.foodcourt.repository;

import com.nisum.foodcourt.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Integer> {

    @Modifying
    @Query("UPDATE MenuItem SET isDeleted = 1 WHERE id =:id")
    public Integer softDeleteMenu(@Param("id") Integer id);


}
