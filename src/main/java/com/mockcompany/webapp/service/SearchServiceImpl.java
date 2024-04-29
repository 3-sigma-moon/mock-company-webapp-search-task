package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Override
    public Collection<ProductItem> searchAll() {
        return this.productItemRepository.findAllProducts();
    }

    private final ProductItemRepository productItemRepository;

    public SearchServiceImpl(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    @Override
    public Collection<ProductItem> search(String query) {
        if (query.startsWith("\"") && query.endsWith("\"")) {
            return this.productItemRepository.findProductItemByNameOrDescription(
                    query.substring(1, query.length() - 1),
                    query.substring(1, query.length() - 1));
        }
        return this.productItemRepository.findProductsByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        //In order to break the tests.

        //        return Collections.EMPTY_LIST;
    }
}
