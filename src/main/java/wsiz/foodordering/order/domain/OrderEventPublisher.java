package wsiz.foodordering.order.domain;

import wsiz.foodordering.order.dto.OrderDto;
import wsiz.foodordering.order.event.OrderFinished;
import wsiz.foodordering.utils.TimeProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class OrderEventPublisher {

  ApplicationEventPublisher eventPublisher;
  TimeProvider timeProvider;

  void notifyOrderFinish(OrderDto finishedOrder) {
    final List<OrderFinished.UserOrderFinished> userOrders = finishedOrder.getUserOrders().stream()
        .map(OrderFinished.UserOrderFinished::fromDto)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    final OrderFinished orderFinished = OrderFinished.builder()
        .orderId(finishedOrder.getId())
        .supplierId(finishedOrder.getSupplierId())
        .channelId(finishedOrder.getChannelId())
        .purchaserId(finishedOrder.getPurchaserId())
        .finishedAt(timeProvider.now())
        .userOrders(userOrders)
        .build();

    eventPublisher.publishEvent(orderFinished);
  }

}
