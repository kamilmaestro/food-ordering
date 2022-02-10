package wsiz.foodordering.domain

import wsiz.foodordering.SecurityContextProvider
import wsiz.foodordering.order.domain.OrderConfiguration
import wsiz.foodordering.order.domain.OrderExpirationConfig
import wsiz.foodordering.order.domain.OrderFacade
import wsiz.foodordering.samples.*
import wsiz.foodordering.supplier.domain.SupplierConfiguration
import wsiz.foodordering.supplier.domain.SupplierFacade
import wsiz.foodordering.utils.FixedTimeProvider
import wsiz.foodordering.utils.TimeProvider
import org.springframework.context.ApplicationEventPublisher

abstract class BasePaymentSpec extends BaseChannelSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {

  ApplicationEventPublisher eventPublisher = Mock()
  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderExpirationConfig expirationConfig = new OrderExpirationConfig()
  TimeProvider timeProvider = new FixedTimeProvider()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade, expirationConfig, timeProvider, eventPublisher)
  PaymentFacade paymentFacade = new PaymentConfiguration().paymentFacade()

  static Long CHANNEL_ID = null

  def setup() {
    logInUser(JOHN)
    expirationConfig.setExpirationAfterMinutes(180)
    CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

}
