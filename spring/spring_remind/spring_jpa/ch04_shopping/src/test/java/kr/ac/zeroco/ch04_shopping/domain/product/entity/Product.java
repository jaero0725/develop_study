package kr.ac.zeroco.ch04_shopping.domain.product.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long itemID;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_PRICE")
    private int price;

    @Column(name = "STOCK_QUANITITY")
    private int stockQuanitity;
}
