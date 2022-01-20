package wsiz.foodordering.order.exception;

import static java.lang.String.join;

public class OrderNotFound extends RuntimeException {

  public OrderNotFound(Long orderId, String status) {
    super("Can not find an order with id: " + orderId + " and status: " + status);
  }

  public OrderNotFound(Long orderId) {
    super("Can not find an order with id: " + orderId);
  }

  public OrderNotFound(Long orderId, String... status) {
    super("Can not find an order with id: " + orderId + " with any of these status: " + join(", ", status));
  }

}