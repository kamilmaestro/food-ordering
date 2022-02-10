package wsiz.foodordering.domain

import wsiz.foodordering.order.dto.OrderDto
import wsiz.foodordering.order.dto.OrderedFoodDto
import wsiz.foodordering.order.dto.ProposalDto
import wsiz.foodordering.utils.EditedOrderTemplate
import spock.lang.Unroll

class EditUserOrderSpec extends BaseOrderSpec {

  def setup() {
    given: "$JOHN is logged in"
      logInUser(JOHN)
    and: "there is a $KRAKOW channel"
      CHANNEL_ID = channelFacade.createChannel(KRAKOW.name).id
  }

  @Unroll
  def "purchaser is able to edit an user order from the finalized order" () {
    given: "there is an order for the $PIZZA_RESTAURANT with user orders added by: $JOHN and $MARC"
      ProposalDto johnProposal = addProposal(PIZZA_RESTAURANT.name)
      logInUser(MARC)
      ProposalDto marcProposal = addProposal(johnProposal.supplierId)
    and: "this order is finalized by $MARC who is a pruchaser"
      OrderDto order = orderFacade.becomePurchaser(newPurchaser(marcProposal.supplierId, marcProposal.channelId))
      OrderDto finalizedOrder = orderFacade.finalizeOrder(order.id)
    when: "$MARC edits $JOHN user order setting new amount: $amount and new price: $price"
      orderFacade.editUserOrder(
          new EditedOrderTemplate(finalizedOrder).forUser(JOHN).editFood(amount, price)
      )
    then: "user order for $JOHN is edited"
    OrderedFoodDto johnOrderedFood = orderFacade.getOrderDto(order.id).userOrders
          .find({ userOrder -> userOrder.orderedFor == JOHN.userId }).orderedFood.first()
      johnOrderedFood.amountOfFood == amount
      johnOrderedFood.foodPrice == price.doubleValue()
    where:
      price | amount
      10.99 | 2
      5.00  | 3
      0.99  | 1
  }

}
