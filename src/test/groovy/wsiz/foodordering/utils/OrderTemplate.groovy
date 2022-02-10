package wsiz.foodordering.utils

import wsiz.foodordering.order.dto.OrderDto
import wsiz.foodordering.order.event.OrderFinished
import wsiz.foodordering.samples.*

import java.time.Instant
import java.util.stream.Collectors

class OrderTemplate implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels {

  private final OrderDto finishedOrder

  OrderTemplate(OrderDto finishedOrder) {
    this.finishedOrder = finishedOrder
  }


  OrderFinished finished(Instant time) {
    final List<OrderFinished.UserOrderFinished> userOrders = finishedOrder.getUserOrders().stream()
        .map({ order ->  OrderFinished.UserOrderFinished.fromDto(order) })
        .flatMap({ orders -> orders.stream() })
        .collect(Collectors.toList())

    return OrderFinished.builder()
        .orderId(finishedOrder.getId())
        .supplierId(finishedOrder.getSupplierId())
        .channelId(finishedOrder.getChannelId())
        .purchaserId(finishedOrder.getPurchaserId())
        .finishedAt(time)
        .userOrders(userOrders)
        .build()
  }

}
