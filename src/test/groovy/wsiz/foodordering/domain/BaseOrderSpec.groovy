package wsiz.foodordering.domain

import wsiz.foodordering.SecurityContextProvider
import wsiz.foodordering.order.dto.OrderDto
import wsiz.foodordering.order.dto.ProposalDto
import wsiz.foodordering.samples.*
import wsiz.foodordering.supplier.domain.SupplierConfiguration
import wsiz.foodordering.supplier.domain.SupplierFacade
import wsiz.foodordering.supplier.dto.FoodDto
import wsiz.foodordering.supplier.dto.SupplierDto
import wsiz.foodordering.utils.FixedTimeProvider
import wsiz.foodordering.utils.TimeProvider
import org.springframework.context.ApplicationEventPublisher

abstract class BaseOrderSpec extends BaseChannelSpec implements SampleUsers, SampleSuppliers, SampleOrders, SampleFood, SampleChannels, SecurityContextProvider {

  ApplicationEventPublisher eventPublisher = Mock()
  SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  OrderExpirationConfig expirationConfig = new OrderExpirationConfig()
  TimeProvider timeProvider = new FixedTimeProvider()
  OrderFacade orderFacade = new OrderConfiguration().orderFacade(supplierFacade, expirationConfig, timeProvider, eventPublisher)

  static Long CHANNEL_ID = null

  def setup() {
    expirationConfig.setExpirationAfterMinutes(180)
    logInUser(JOHN)
    CHANNEL_ID = channelFacade.createChannel("channel").id
  }

  OrderDto newFinalizedOrder(String supplierName) {
    ProposalDto proposal = addProposal(supplierName)
    OrderDto order = orderFacade.becomePurchaser(newPurchaser(proposal.supplierId, proposal.channelId))

    return orderFacade.finalizeOrder(order.id)
  }

  ProposalDto addProposal(String supplierName) {
    return addProposal(supplierName, CHANNEL_ID)
  }

  ProposalDto addProposal(long supplierId) {
    return addProposalForSupplier(supplierId, CHANNEL_ID)
  }

  ProposalDto addProposal(long supplierId, long channelId) {
    return addProposalForSupplier(supplierId, channelId)
  }

  ProposalDto addProposal(String supplierName, long channelId) {
    SupplierDto supplier = supplierFacade.addSupplier(newSupplier(name: supplierName))
    return addProposalForSupplier(supplier.id, channelId)
  }

  private ProposalDto addProposalForSupplier(long supplierId, long channelId) {
    FoodDto food = supplierFacade.addFoodToSupplierMenu(newFood(supplierId: supplierId))
    return orderFacade.createProposal(newProposal(supplierId: supplierId, foodId: food.id, channelId: channelId))
  }

}
