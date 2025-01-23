package net.nemisolv.inventoryservice.repository;

import net.nemisolv.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {


    // common case: if the product is purchased, quantity will be passed as negative , and vice versa
//    @Modifying
//    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :quantityDelta WHERE i.productId = :productId")
//    void updateStock(Long productId, int quantityDelta);


    @Query("SELECT i.quantity FROM Inventory i WHERE i.productId = :productId")
    int getStock(Long productId);

    Inventory findByProductId(Long productId);


}
