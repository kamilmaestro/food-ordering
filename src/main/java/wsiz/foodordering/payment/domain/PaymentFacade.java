package wsiz.foodordering.payment.domain;

import wsiz.foodordering.infrastructure.PageInfo;
import wsiz.foodordering.order.event.OrderFinished;
import wsiz.foodordering.payment.dto.PaymentDto;
import wsiz.foodordering.payment.exception.PaymentNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.Set;

import static wsiz.foodordering.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static wsiz.foodordering.payment.domain.PaymentStatus.PAID_OFF;

@Transactional
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentFacade {

  PaymentRepository paymentRepository;
  PaymentCreator paymentCreator;

  public Page<PaymentDto> findUserCharges(PageInfo pageInfo) {
    return paymentRepository.findAllByPayerIdAndStatusNot(getLoggedUserId(), PAID_OFF, pageInfo.toPageRequest())
        .map(Payment::dto);
  }

  public Page<PaymentDto> findUserMoneyDue(PageInfo pageInfo) {
    return paymentRepository.findAllByPurchaserIdAndStatusNot(getLoggedUserId(), PAID_OFF, pageInfo.toPageRequest())
        .map(Payment::dto);
  }

  public Page<PaymentDto> getArchivalPayments(PageInfo pageInfo) {
    return paymentRepository.findAllPaidOffByUserId(getLoggedUserId(), pageInfo.toPageRequest())
        .map(Payment::dto);
  }

  public void marksAsPaidOff(long paymentId) {
    final Payment payment = getPayment(paymentId);
    final Payment updatedPayment = payment.marksAsPaidOff();
    paymentRepository.save(updatedPayment);
  }

  @EventListener
  void onOrderFinished(OrderFinished orderFinished) {
    final Set<Payment> payments = paymentCreator.createPayments(orderFinished);
    paymentRepository.saveAll(payments);
  }

  private Payment getPayment(long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new PaymentNotFound(paymentId));
  }

}
