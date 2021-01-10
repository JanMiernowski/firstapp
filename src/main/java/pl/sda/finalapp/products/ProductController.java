package pl.sda.finalapp.products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.finalapp.categories.CategoryService;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping// todo dodac wyszukiwanie po kategorii
    String productList(@RequestParam(value = "query", required = false) String query,
                       @RequestParam(value = "productType", required = false) ProductType productType,
                       Model model) {
        model.addAttribute("productsList", productService.findProducts(query, productType));
        model.addAttribute("searchText", query);
        model.addAttribute("productType", productType);
        model.addAttribute("productTypes", ProductType.values());
        model.addAttribute("categoriesWithId", categoryService.prepareCategoriesWithId());
        return "productsPage";
    }

    @PostMapping
    String addProduct(@RequestParam String title,
                      @RequestParam String pictureUrl,
                      @RequestParam BigDecimal price,
                      @RequestParam ProductType productType,
                      @RequestParam Integer categoryId){
        ProductDto dto = new ProductDto(title, pictureUrl, price, productType, categoryId);
        productService.addProduct(dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    String editProductForm(@PathVariable Integer id, Model model){
        Optional<ProductDto> productById = productService.findProductById(id);
        if(productById.isEmpty()){
            return "redirect:/products";
        }
        model.addAttribute("product", productById.get());
        model.addAttribute("productTypeList", ProductType.values());
        model.addAttribute("categoryWithIdDtoList", categoryService.prepareCategoriesWithId());
        return "editProductPage";
    }

    @PostMapping("/{id}")//todo security check if id != product.id
    String editProduct(@ModelAttribute ProductDto product, @PathVariable Integer id){
        productService.update(product);
        return "redirect:/products";
    }



}
