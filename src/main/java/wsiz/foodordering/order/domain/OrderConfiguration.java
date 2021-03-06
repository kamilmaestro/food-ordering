package wsiz.foodordering.order.domain;

import wsiz.foodordering.supplier.domain.SupplierFacade;
import wsiz.foodordering.utils.TimeProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

  public OrderFacade orderFacade(SupplierFacade supplierFacade,
                          OrderExpirationConfig expirationConfig,
                          TimeProvider timeProvider,
                          ApplicationEventPublisher eventPublisher) {
    return orderFacade(
        supplierFacade, expirationConfig, timeProvider, eventPublisher, new InMemoryProposalRepository(), new InMemoryOrderRepository()
    );
  }

  @Bean
  OrderFacade orderFacade(SupplierFacade supplierFacade,
                          OrderExpirationConfig expirationConfig,
                          TimeProvider timeProvider,
                          ApplicationEventPublisher eventPublisher,
                          ProposalRepository proposalRepository,
                          OrderRepository orderRepository) {
    return OrderFacade.builder()
        .supplierFacade(supplierFacade)
        .timeProvider(timeProvider)
        .proposalRepository(proposalRepository)
        .orderRepository(orderRepository)
        .orderCreator(new OrderCreator(supplierFacade, expirationConfig, timeProvider))
        .eventPublisher(new OrderEventPublisher(eventPublisher, timeProvider))
        .userOrderRemover(new UserOrderRemover(orderRepository))
        .build();
  }

}
