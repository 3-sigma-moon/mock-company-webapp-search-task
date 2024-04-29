package com.mockcompany.webapp.service;

import com.mockcompany.webapp.model.ProductItem;

import java.util.Collection;

public interface SearchService {
    Collection<ProductItem> search(String query);
    Collection<ProductItem> searchAll();

}
