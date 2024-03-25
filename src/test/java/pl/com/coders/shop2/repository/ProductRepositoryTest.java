package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.CategoryType;
import pl.com.coders.shop2.domain.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = createSampleCategory();
        categoryRepository.add(category);
        product = createSampleProduct(category);
        productRepository.add(product);
    }

    @Test
    void addProduct() {
        //given
            //product from setUp
        //when
        Product addedProduct = productRepository.add(product);
        //then
        assertNotNull(addedProduct,"Dodawany produkt nie istnieje");
        assertNotNull(addedProduct.getCreated(),"Czas utworzenia nowego produktu nie został zapisany");
        assertNotNull(addedProduct.getId(),"Id nowego produktu nie zostało utworzone");
    }

    @Test
    void getProduct() {
        //given
        Product addedProduct = productRepository.add(product);
        //when
        Product gotProduct = productRepository.get(addedProduct.getId());
        //then
        assertNotNull(gotProduct,"Metoda get nie pobrała produktu");
        assertEquals(addedProduct,gotProduct,"Pobrany produkt jest inny niż zapisany");
    }

    @Test
    void deleteProduct() {
        //given
        Product addedProduct = productRepository.add(product);
        //when
        boolean isRemoved = productRepository.delete(addedProduct.getId());
        //then
        assertTrue(isRemoved,"Produkt nie został usunięty");
    }

    @Test
    void updateProduct() {
        //given
        Product addedProduct = productRepository.add(product);
        Product modifiedProduct = Product.builder()
                .name("changedName")
                .description("changedDescription")
                .price(BigDecimal.valueOf(999.99))
                .quantity(999)
                .category(category)
                .build();
        //when
        Product updatedProduct = productRepository.update(modifiedProduct,addedProduct.getId());
        //then
        assertEquals(addedProduct.getId(),updatedProduct.getId(),"Uaktualniony produkt ma inne id niż wcześniej");
        assertEquals(modifiedProduct.getPrice(),updatedProduct.getPrice(),"Cena zmodyfikowanego produktu inne niż modyfikacja");
        assertEquals(modifiedProduct.getDescription(),updatedProduct.getDescription(),"Opis zmodyfikowanego produktu inny niż modyfikacja");
        assertEquals(modifiedProduct.getName(),updatedProduct.getName(),"Nazwa zmodyfikowanego produktu inna niż modyfikacja");
        assertEquals(modifiedProduct.getQuantity(),updatedProduct.getQuantity(),"Ilość zmodyfikowanego produktu inna niż modyfikacja");
    }

    @Test
    void getProductByName() {
        //given
        Product addedProduct = createSampleProduct(category);
        addedProduct.setName("NewProduct");
        productRepository.add(addedProduct);
        //when
        Product gotProduct = productRepository.getByName("NewProduct");
        //then
        assertNotNull(gotProduct,"Metoda get nie pobrała produktu");
        assertEquals(addedProduct,gotProduct,"Pobrany produkt jest inny niż zapisany");
    }

    @Test
    void addProductByCreateOrUpdateMethod(){
        //given
        //product from setUp
        //when
        Product addedProduct = productRepository.createOrUpdate(product);
        //then
        assertNotNull(addedProduct,"Dodawany produkt nie istnieje");
        assertNotNull(addedProduct.getCreated(),"Czas utworzenia nowego produktu nie został zapisany");
        assertNotNull(addedProduct.getId(),"Id nowego produktu nie zostało utworzone");
    }

    @Test
    void updateProductByCreateOrUpdateMethod() {
        //given
        Product modifiedProduct = productRepository.add(product);
        modifiedProduct.setName("changedName");
        modifiedProduct.setDescription("changedDescription");
        modifiedProduct.setPrice(BigDecimal.valueOf(999.99));
        modifiedProduct.setCategory(category);
        //when
        Product updatedProduct = productRepository.createOrUpdate(modifiedProduct);
        //then
        assertEquals(modifiedProduct.getId(),updatedProduct.getId(),"Uaktualniony produkt ma inne id niż wcześniej");
        assertEquals(modifiedProduct.getPrice(),updatedProduct.getPrice(),"Cena zmodyfikowanego produktu inne niż modyfikacja");
        assertEquals(modifiedProduct.getDescription(),updatedProduct.getDescription(),"Opis zmodyfikowanego produktu inny niż modyfikacja");
        assertEquals(modifiedProduct.getName(),updatedProduct.getName(),"Nazwa zmodyfikowanego produktu inna niż modyfikacja");
        assertEquals(modifiedProduct.getQuantity(),updatedProduct.getQuantity(),"Ilość zmodyfikowanego produktu inna niż modyfikacja");
    }

    @Test
    void findAllProductsByCategory() {
        //given
        //category from init method
        //product from init method
        Category secondCategory = Category.builder().name(CategoryType.MOTORYZACJA.name()).build();
        categoryRepository.add(secondCategory);
        Product secondProductOfFirstCategory = createSampleProduct(category);
        Product thirdProductOfSecondCategory = createSampleProduct(secondCategory);
        productRepository.add(secondProductOfFirstCategory);
        productRepository.add(thirdProductOfSecondCategory);
        //when
        List<Product> productListElektronika = productRepository.findAllProductsByCategory(CategoryType.ELEKTRONIKA);
        List<Product> productListInne = productRepository.findAllProductsByCategory(CategoryType.MOTORYZACJA);

        //then
        assertEquals(2, productListElektronika.size());
        assertEquals(1,productListInne.size());
    }

    private Product createSampleProduct(Category category) {
        return Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .category(category)
                .build();
    }

    private Category createSampleCategory() {
        return Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
    }
}