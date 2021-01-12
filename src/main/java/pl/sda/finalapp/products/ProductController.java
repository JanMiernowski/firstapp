package pl.sda.finalapp.products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalapp.categories.CategoryDto;
import pl.sda.finalapp.categories.CategoryService;
import pl.sda.finalapp.exceprions.WrongIdException;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private Integer categoryId;

    private ProductType productType;

    private String query;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping()
// todo dodac wyszukiwanie po kategorii
    String productList(@RequestParam(value = "query", required = false) String query,
                       @RequestParam(value = "productType", required = false) ProductType productType,
                       @RequestParam(value = "categoryId", required = false) Integer categoryId,
                       Model model) {
        this.query = query;
        this.productType = productType;
        this.categoryId = categoryId;
        model.addAttribute("productsList", productService.findProducts(this.query, this.productType, this.categoryId));
        model.addAttribute("searchText", this.query);
        model.addAttribute("productType", this.productType);
        model.addAttribute("categoryId", this.categoryId);
        model.addAttribute("productTypes", ProductType.values());
        model.addAttribute("categoriesWithId", categoryService.prepareCategoriesWithId());
        return "productsPage";
    }

    @GetMapping("/category{categoryId}")
    String productListWithCategoryFilter(@PathVariable(value = "categoryId", required = false) Integer categoryId,
                                         Model model){
        this.categoryId = categoryId;
        return productList(this.query, this.productType, this.categoryId, model);
    }

    @GetMapping("/category0")
    String resetCategoryFilter(Model model){
        this.categoryId = null;
        return productList(this.query, this.productType,this.categoryId, model);
    }

    @PostMapping
    String addProduct(@RequestParam String title,
                      @RequestParam String pictureUrl,
                      @RequestParam BigDecimal price,
                      @RequestParam ProductType productType,
                      @RequestParam Integer addCategoryId) {
        ProductDto dto = new ProductDto(title, pictureUrl, price, productType, addCategoryId);
        productService.addProduct(dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    String editProductForm(@PathVariable Integer id, Model model) {
        Optional<ProductDto> productById = productService.findProductById(id);
        if (productById.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", productById.get());
        model.addAttribute("productTypeList", ProductType.values());
        model.addAttribute("categoryWithIdDtoList", categoryService.prepareCategoriesWithId());
        return "editProductPage";
    }

    @PostMapping("/{id}")
//todo security check if id != product.id
    String editProduct(@ModelAttribute ProductDto product, @PathVariable Integer id) throws WrongIdException {
        if (!product.getId().equals(id)) {
             throw new WrongIdException(product.getId(), id);
        }
        productService.update(product);
        return "redirect:/products";
    }


}
