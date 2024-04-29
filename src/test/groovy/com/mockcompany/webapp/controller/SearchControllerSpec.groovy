package com.mockcompany.webapp.controller

import com.mockcompany.webapp.data.ProductItemRepository
import com.mockcompany.webapp.model.ProductItem
import org.hibernate.annotations.GeneratorType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.DisplayNameGenerator
import org.junit.jupiter.api.MethodOrderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cglib.beans.BulkBean
import spock.lang.Specification

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.Simple)
class SearchControllerSpec extends Specification {

    @Autowired
    ProductItemRepository productItemRepository
    @Autowired
    SearchController searchController

    void setupMockData(Collection<ProductItem> mockItems) {
        productItemRepository.deleteAll()
        productItemRepository.saveAll(mockItems)
    }

    Set<ProductItem> doSearch(String query) {
        return searchController.search(query) as Set
    }

    ProductItem createItem(String name, String description = name) {
        return new ProductItem(name: name, description: description, cost: 100, image: "")
    }


    def "search will return an item that matches the name of the product"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")

        and:
        setupMockData([item1, item2])

        when:
        Set<ProductItem> results = doSearch("Will")

        then:
        results.contains(item1)
        !results.contains(item2)
    }

    @DisplayName("search will return multiple items when there are matches in the name")
    def "search will return multiple items when there are matches in the name"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")
        ProductItem item3 = createItem("This Too, Will Match", "This is an OK item")

        and:
        setupMockData([item1, item2, item3])

        when:
        Set<ProductItem> results = doSearch("Will")

        then:
        results.contains(item1)
        !results.contains(item2)
        results.contains(item3)
    }

    @DisplayName("search is case insensitive")
    def "search is case insensitive"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")

        and:
        setupMockData([item1, item2])

        when: "lowercase search"
        Set<ProductItem> results = doSearch("will")

        then:
        results.contains(item1)
        !results.contains(item2)
    }

    @DisplayName("search will return an item that matches the description of the product")
    def "search will return an item that matches the description of the product"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")

        and:
        setupMockData([item1, item2])

        when:
        Set<ProductItem> results = doSearch("good")

        then:
        results.contains(item1)
        !results.contains(item2)
    }

    def "search will return multiple items when there are matches in the description"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")
        ProductItem item3 = createItem("This Too, Will Match", "This is also a good item")

        and:
        setupMockData([item1, item2, item3])

        when:
        Set<ProductItem> results = doSearch("good")

        then:
        results.contains(item1)
        !results.contains(item2)
        results.contains(item3)
    }

    def "search will return items if name or description match the search"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")
        ProductItem item3 = createItem("Another Product", "This product will also match")

        and:
        setupMockData([item1, item2, item3])

        when:
        Set<ProductItem> results = doSearch("will")

        then:
        results.contains(item1) // Matches Will on name
        !results.contains(item2)
        results.contains(item3) // Matches will on description
    }

    def "search will only match exactly on name or description when quotes are used"() {
        given:
        ProductItem item1 = createItem("Will Match", "This is a good item")
        ProductItem item2 = createItem("Won't Match", "This is a better item")
        ProductItem item3 = createItem("Another Product", "Will Match")
        ProductItem item4 = createItem("Will Match. Nevermind", "Should not actually match")

        and:
        setupMockData([item1, item2, item3])

        when:
        Set<ProductItem> results = doSearch('"Will Match"')

        then:
        results.contains(item1) // Matches on name
        !results.contains(item2)
        results.contains(item3) // Matches on description
        !results.contains(item4)
    }
}
