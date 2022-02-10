package wsiz.foodordering.order.domain;

import wsiz.foodordering.order.dto.OrderStatusDto;

enum OrderStatus {

  CANCELLED,
  FINALIZED,
  FINISHED,
  ORDERED;

  OrderStatusDto dto() {
    switch (this) {
      case CANCELLED:
        return OrderStatusDto.CANCELLED;
      case FINALIZED:
        return OrderStatusDto.FINALIZED;
      case FINISHED:
        return OrderStatusDto.FINISHED;
      case ORDERED:
        return OrderStatusDto.ORDERED;
      default:
        throw new IllegalStateException("Can not obtain order status dto");
    }
  }

}
