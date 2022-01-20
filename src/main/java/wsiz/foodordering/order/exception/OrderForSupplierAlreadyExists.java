package wsiz.foodordering.order.exception;

public class OrderForSupplierAlreadyExists extends RuntimeException {

  public OrderForSupplierAlreadyExists(String message) {
    super(message);
  }

}