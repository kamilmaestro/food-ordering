package wsiz.foodordering.order.domain;

import wsiz.foodordering.order.dto.OrderDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import static wsiz.foodordering.order.domain.OrderStatus.ORDERED;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
final class UserOrderRemover {

  OrderRepository orderRepository;

  OrderDto removeUserOrder(long userOrderId, Order order) {
    if (order.getStatus().equals(ORDERED)) {
      final AcceptedOrder editedOrder = order.removeUserOrderFromAcceptedOrder(userOrderId);
      return orderRepository.saveOrder(editedOrder).acceptedDto();
    } else {
      final FinalizedOrder editedOrder = order.removeUserOrderFromFinalizedOrder(userOrderId);
      return orderRepository.saveOrder(editedOrder).finalizedDto();
    }
  }

}
