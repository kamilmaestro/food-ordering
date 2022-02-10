package wsiz.foodordering.order.domain;

import wsiz.foodordering.order.dto.EditUserOrderDto;
import wsiz.foodordering.order.dto.OrderDto;

interface FinalizedOrder {

  OrderDto finalizedDto();

  FinalizedOrder removeUserOrderFromFinalizedOrder(long userOrderId);

  FinalizedOrder editUserOrder(EditUserOrderDto editUserOrder);

  CancelledOrder resignFromFinalizedOrder();

  FinishedOrder finishOrder();

}
