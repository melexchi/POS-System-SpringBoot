package com.Melexworld.controller;

import com.Melexworld.domain.StoreStatus;
import com.Melexworld.exceptions.UserException;
import com.Melexworld.mapper.StoreMapper;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.StoreDTO;
import com.Melexworld.payload.response.ApiResponse;
import com.Melexworld.service.StoreService;
import com.Melexworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {


    private final StoreService storeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody  StoreDTO storeDTO, @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.getUserFromJwtToken(jwt);

        return  ResponseEntity.ok(storeService.createStore(storeDTO,user));

    }



    @GetMapping()
    public ResponseEntity<List<StoreDTO>> getAllStore(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.getUserFromJwtToken(jwt);

        return  ResponseEntity.ok(storeService.getAllStores());

    }


    @GetMapping("/admin")
    public ResponseEntity<StoreDTO> getStoreByAdmin( @RequestHeader("Authorization") String jwt) throws Exception {

        return  ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));

    }

    @GetMapping("/employee")
    public ResponseEntity<StoreDTO> getStoreByEmployee( @RequestHeader("Authorization") String jwt) throws Exception {

        return  ResponseEntity.ok(storeService.getStoreByEmployee());

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDTO) throws  Exception{

        return ResponseEntity.ok(storeService.updateStore(id,storeDTO));
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id) throws  Exception{

        storeService.deleteStore(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Store Deleted Successfully");

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {

        return  ResponseEntity.ok(storeService.getStoreById(id));

    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<StoreDTO> moderateStore (@PathVariable Long id, @RequestParam StoreStatus status)throws Exception{

        return ResponseEntity.ok(storeService.moderateStore(id, status));
    }

}
