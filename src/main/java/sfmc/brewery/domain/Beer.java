package sfmc.brewery.domain;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@NoArgsConstructor

@Entity
public class Beer implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(unique = true)
    @Setter String upc;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Setter private String beerName;
    @Setter private String beerType;

    @Setter private BigDecimal price;
    @Setter private Integer minOnHand;
    @Setter private Integer quantityToBrew;

    @Builder
    public Beer(String beerName, String beerType, BigDecimal price, Integer minOnHand, Integer quantityToBrew, String upc) {
        this.beerName = beerName;
        this.beerType = beerType;
        this.price = price;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
        this.upc = upc;
    }
}
