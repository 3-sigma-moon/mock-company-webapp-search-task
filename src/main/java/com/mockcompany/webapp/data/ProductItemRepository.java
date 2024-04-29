package com.mockcompany.webapp.data;

import com.mockcompany.webapp.model.ProductItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductItemRepository extends CrudRepository<ProductItem, Long> {
    Collection<ProductItem> findProductsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String query, String query1);
    Collection<ProductItem> findProductItemByNameOrDescription(String query, String query1);
    @Query("select p from ProductItem  p")
    Collection<ProductItem> findAllProducts();

}
