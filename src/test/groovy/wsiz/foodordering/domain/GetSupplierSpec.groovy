package wsiz.foodordering.domain

import wsiz.foodordering.infrastructure.PageInfo
import wsiz.foodordering.samples.SampleFood
import wsiz.foodordering.samples.SampleSuppliers
import wsiz.foodordering.supplier.dto.SupplierDto
import wsiz.foodordering.supplier.exception.SupplierNotFound
import org.springframework.data.domain.Page
import spock.lang.Specification

class GetSupplierSpec extends Specification implements SampleSuppliers, SampleFood {

  private SupplierFacade supplierFacade = new SupplierConfiguration().supplierFacade()
  private PageInfo pageInfo = new PageInfo(0, 10)

  def "should be able to see all suppliers" () {
    given: "there are $PIZZA_RESTAURANT, $KEBAB_RESTAURANT and $APPLE_RESTAURANT suppliers"
      withSampleSuppliers(supplierFacade, PIZZA_RESTAURANT, KEBAB_RESTAURANT, APPLE_RESTAURANT)
    when: "wants to get all suppliers"
      Page<SupplierDto> suppliers = supplierFacade.findAllSuppliers(pageInfo)
    then: "gets those suppliers"
      suppliers.content.name.sort() == [PIZZA_RESTAURANT.name, KEBAB_RESTAURANT.name, APPLE_RESTAURANT.name].sort()
  }

  def "should not get not existing supplier" () {
    when: "wants to get not existing supplier"
      supplierFacade.getSupplierDto(FAKE_SUPPLIER_ID)
    then: "there is no such supplier"
      thrown(SupplierNotFound)
  }

}
