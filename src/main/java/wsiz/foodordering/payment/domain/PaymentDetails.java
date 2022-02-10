package wsiz.foodordering.payment.domain;

import wsiz.foodordering.order.domain.AmountOfFood;
import wsiz.foodordering.payment.dto.PaymentDto;
import wsiz.foodordering.supplier.domain.Money;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payment_details")
class PaymentDetails implements Serializable {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "payment_uuid")
  String paymentUuid;

  @Column(name = "food_name")
  String foodName;

  @Embedded
  @AttributeOverride(name = "amount", column = @Column(name = "amount_of_food"))
  AmountOfFood amountOfFood;

  @Embedded
  @AttributeOverride(name = "price", column = @Column(name = "food_price"))
  Money foodPrice;

  @Builder
  PaymentDetails(String paymentUuid, String foodName, Integer amountOfFood, Double foodPrice) {
    this.paymentUuid = paymentUuid;
    this.foodName = foodName;
    this.amountOfFood = new AmountOfFood(amountOfFood);
    this.foodPrice = new Money(foodPrice);
  }

  PaymentDto.PaymentDetailsDto dto() {
    return new PaymentDto.PaymentDetailsDto(
        this.foodName, this.amountOfFood.getValue(), this.foodPrice.getValueAsDouble()
    );
  }

}
