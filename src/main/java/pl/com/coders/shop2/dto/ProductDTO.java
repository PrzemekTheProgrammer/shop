package pl.com.coders.shop2.dto;

import lombok.Builder;
import lombok.Data;
import pl.com.coders.shop2.domain.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private CategoryType categoryType;
    private LocalDateTime created;
    private LocalDateTime updated;
}
