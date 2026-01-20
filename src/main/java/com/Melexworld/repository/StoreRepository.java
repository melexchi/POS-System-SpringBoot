package com.Melexworld.repository;

import com.Melexworld.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {


    Store findByStoreAdminId(Long adminId);
}
