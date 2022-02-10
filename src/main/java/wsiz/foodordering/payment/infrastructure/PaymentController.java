package wsiz.foodordering.payment.infrastructure;

import wsiz.foodordering.infrastructure.PageInfo;
import wsiz.foodordering.payment.domain.PaymentFacade;
import wsiz.foodordering.payment.dto.PaymentDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payment")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PaymentController {

  PaymentFacade paymentFacade;

  @Autowired
  PaymentController(PaymentFacade paymentFacade) {
    this.paymentFacade = paymentFacade;
  }

  @GetMapping("/charges/")
  public ResponseEntity<Page<PaymentDto>> findUserCharges(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.findUserCharges(pageInfo));
  }

  @GetMapping("/due/")
  public ResponseEntity<Page<PaymentDto>> findUserMoneyDue(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.findUserMoneyDue(pageInfo));
  }

  @GetMapping("/archival/")
  public ResponseEntity<Page<PaymentDto>> getArchivalPayments(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(paymentFacade.getArchivalPayments(pageInfo));
  }

  @PostMapping("/{paymentId}")
  public ResponseEntity<Void> marksAsPaidOff(@PathVariable long paymentId) {
    paymentFacade.marksAsPaidOff(paymentId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
