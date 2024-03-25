package pl.com.coders.shop2.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_item")
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId;

    private int quantity;
    private BigDecimal totalPrice;
    private int cartIndex;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CartItemId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "cart_id")
        private Cart cart;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private Product product;
    }
}

