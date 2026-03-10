package com.Melexworld.service.impl;

import com.Melexworld.mapper.InventoryMapper;
import com.Melexworld.model.Branch;
import com.Melexworld.model.Inventory;
import com.Melexworld.model.Product;
import com.Melexworld.payload.dto.InventoryDTO;
import com.Melexworld.repository.BranchRepository;
import com.Melexworld.repository.InventoryRepository;
import com.Melexworld.repository.ProductRepository;
import com.Melexworld.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {

        Branch branch = branchRepository.findById(inventoryDTO.getId()).orElseThrow(
                ()-> new Exception("Branch Does not exist...")
        );

        Product product = productRepository.findById(inventoryDTO.getProductId()).orElseThrow(
                ()-> new Exception("Product does not exist..")
        );

        Inventory inventory = InventoryMapper.toEntity(inventoryDTO,branch, product);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return InventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id,InventoryDTO inventoryDTO) throws Exception {

        Inventory inventory= inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found")
        );

        inventory.setQuantity(inventoryDTO.getQuantity());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory does not exist..")
        );

        inventoryRepository.delete(inventory);

    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(
                ()-> new Exception("Inventory not found")
        );

        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {

        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId,branchId);

        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) {

        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);

        return inventories.stream().map(
                InventoryMapper::toDTO
        ).collect(Collectors.toList());
    }
}
