package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    /**
     * The people that wrote this code didn't know about JPA Spring Repository interfaces!
     */
    private final EntityManager entityManager;

    private SearchServiceImpl searchService;

    public ReportController(EntityManager entityManager, SearchServiceImpl searchService) {
        this.entityManager = entityManager;
        this.searchService = searchService;
    }

    @Autowired
    public ReportController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        int count = this.searchService.searchAll().size();
        List<Number> matchingIds = new ArrayList<>();
        matchingIds.addAll(Collections.singleton(this.searchService.search("cool").size()));
        matchingIds.addAll(Collections.singleton(this.searchService.search("Cool").size()));
        List<Number> counted = new ArrayList<>();
        for (Number id : matchingIds) {
            if (!counted.contains(id)) {
                counted.add(id);
            }
        }
        response.getSearchTermHits().put("Cool", counted.size());
        response.setProductCount(count);
        List<ProductItem> allItems = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList();
        int kidCount = this.searchService.search("Kids").size();
        int perfectCount = this.searchService.search("Perfect").size();
        response.getSearchTermHits().put("Kids", kidCount);
        response.getSearchTermHits().put("Amazing", this.searchService.search("amazing").size());
        hits.put("Perfect", perfectCount);
        return response;
    }
}
