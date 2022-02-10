package wsiz.foodordering.order.domain;

import wsiz.foodordering.order.dto.OrderDto;

interface AcceptedOrder {

  OrderDto acceptedDto();

  AcceptedOrder removeUserOrderFromAcceptedOrder(long userOrderId);

  CancelledOrder resignFromAcceptedOrder();

  FinalizedOrder finalizeOrder();

}
