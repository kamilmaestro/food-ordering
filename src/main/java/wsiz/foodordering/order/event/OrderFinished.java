package wsiz.foodordering.order.event;

import wsiz.foodordering.order.dto.UserOrderDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class OrderFinished {

  long orderId;
  long supplierId;
  long channelId;
  long purchaserId;
  Instant finishedAt;
  Collection<UserOrderFinished> userOrders;

  @Builder
  @Getter
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class UserOrderFinished {

    long userId;
    String foodName;
    int amountOfFood;
    double foodPrice;

    public static Collection<UserOrderFinished> fromDto(UserOrderDto dto) {
      return dto.getOrderedFood().stream()
          .map(orderedFood -> {
            return UserOrderFinished.builder()
                .userId(dto.getOrderedFor())
                .foodName(orderedFood.getFoodName())
                .amountOfFood(orderedFood.getAmountOfFood())
                .foodPrice(orderedFood.getFoodPrice())
                .build();
          }).collect(toList());
    }

  }

}