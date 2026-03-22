package net.parksy.dbmulti.reporting.service;

import net.parksy.dbmulti.reporting.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @Transactional("reportingTransactionManager")
    void testCreateAndGetProduct() {
        // Given
        String name = "Laptop";
        Double price = 999.99;

        // When
        Product savedProduct = productService.createProduct(name, price);

        // Then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(name);
        assertThat(savedProduct.getPrice()).isEqualTo(price);

        // And When
        Optional<Product> foundProduct = productService.getProductByName(name);

        // Then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getPrice()).isEqualTo(price);
    }

    @Test
    @Transactional("reportingTransactionManager")
    void testGetAllProducts() {
        // Given
        productService.createProduct("Mouse", 25.0);
        productService.createProduct("Keyboard", 75.0);

        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Mouse", "Keyboard");
    }
}
