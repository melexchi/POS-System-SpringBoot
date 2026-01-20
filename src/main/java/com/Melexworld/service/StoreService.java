package com.Melexworld.service;


import com.Melexworld.domain.StoreStatus;
import com.Melexworld.exceptions.UserException;
import com.Melexworld.model.Store;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO, User user);

    StoreDTO getStoreById(Long id) throws Exception;

    List<StoreDTO> getAllStores();

    Store getStoreByAdmin() throws UserException;

    StoreDTO updateStore(Long id, StoreDTO storeDTO) throws Exception;

    void deleteStore(Long id) throws UserException;

    StoreDTO getStoreByEmployee() throws UserException;

    StoreDTO moderateStore(Long id, StoreStatus status) throws  Exception;
}
