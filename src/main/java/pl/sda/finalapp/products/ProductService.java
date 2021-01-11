package pl.sda.finalapp.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.finalapp.categories.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ProductListDto> findProducts(String query, ProductType productType, Integer categoryId) {
        List<Product> products;
        if ((query == null || query.isBlank()) && productType == null && categoryId==null) {
            products = productRepository.findAll();
        } else if (productType != null && (query != null && !query.isBlank()) && categoryId==null) {
            products = productRepository.findByProductTypeAndTitle(query, productType);
        } else if (productType != null && categoryId==null) {
            products = productRepository.findByProductType(productType);
        } else if ( categoryId==null) {
            products = productRepository.findByTitle(query);
        } else if((query==null || query.isBlank()) && productType==null){
            products = productRepository.findByCategoryId(categoryId);
        } else if( query!=null && !query.isBlank() && productType==null){
            products = productRepository.findByCategoryIdAndQuery(query,categoryId);
        }  else {
            products = productRepository.findByCategoryIdAndQueryAndProductType(query,productType,categoryId);
        }
        return products.stream()
                .map(p -> createProductListDto(p))
                .collect(Collectors.toList());
    }

    private ProductListDto createProductListDto(Product p) {
        String categoryName = categoryService.findCategoryNameById(p.getCategoryId())
                .orElseGet(() -> createErrorText(p));
        return p.toListDto(categoryName);
    }

    private String createErrorText(Product p) {
        return "BŁĄD " + p.getCategoryId();
    }

    public void addProduct(ProductDto dto) {
        Product product = Product.fromDto(dto);
        productRepository.save(product);
    }

    public Optional<ProductDto> findProductById(Integer id) {
        return productRepository.findById(id)
                .map(p -> p.toDto());
    }

    public void update(ProductDto dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o id: " + dto.getId()));
        product.apply(dto);
        productRepository.save(product);
    }
}
