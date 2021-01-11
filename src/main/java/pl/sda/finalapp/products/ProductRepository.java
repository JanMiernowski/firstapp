package pl.sda.finalapp.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where upper(p.title) like concat('%',upper(?1), '%')")
    List<Product> findByTitle(String query);

    @Query("select p from Product p where p.productType = ?1")
    List<Product> findByProductType(ProductType productType);

    @Query("select p" +
            " from Product p" +
            " where upper(p.title) like concat('%',upper(?1), '%')" +
            " and  p.productType = ?2")
    List<Product> findByProductTypeAndTitle(String query, ProductType productType);

    List<Product> findByCategoryId(Integer categoryId);

    @Query("select p " +
            "from Product p " +
            "where upper(p.title) like concat('%',upper(?1), '%') " +
            "and p.categoryId = ?2")
    List<Product> findByCategoryIdAndQuery(String query, Integer categoryId);

    @Query("select p " +
            "from Product p " +
            "where upper(p.title) like concat('%',upper(?1), '%') " +
            "and p.productType = ?2 " +
            "and p.categoryId = ?3")
    List<Product> findByCategoryIdAndQueryAndProductType(String query, ProductType productType, Integer categoryId);
}
