package com.example.SpringBootEcommerceBackend.api.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootEcommerceBackend.domain.inventory.Inventory;
import com.example.SpringBootEcommerceBackend.domain.product.Product;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.InventoryRepository;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.ProductRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    ProductController(ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    record CreateProductRequest(
            @NotBlank String sku,
            @NotBlank String name,
            @Min(0) long priceCents,
            @Min(0) long initialStock) {
    }

    record ProductResponse(Long id, String sku, String name, long priceCents, long availableQty) {
    }

    @PostMapping
    public ProductResponse create(@RequestBody CreateProductRequest req) {
        var p = productRepository.save(new Product(req.sku(), req.name(), req.priceCents()));
        inventoryRepository.save(new Inventory(p, req.initialStock()));
        return new ProductResponse(p.getId(), p.getSku(), p.getName(), p.getPriceCents(), req.initialStock());
    }

    @GetMapping
    public Page<ProductResponse> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by("id").descending());

        // this will first query products with pagination, then for each product, it will query inventory to get available quantity for each product. This is not efficient, but it's simple and works for demonstration purposes. In a real application, you might want to optimize this by using a custom query that joins products and inventory in one go.
        return productRepository.findByActiveTrue(pageable)
                .map(p -> new ProductResponse(p.getId(), p.getSku(), p.getName(), p.getPriceCents(),
                        inventoryRepository.findById(p.getId()).map(Inventory::getAvailableQty).orElse(0L)
                ));
    }

}
