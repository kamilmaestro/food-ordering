package wsiz.foodordering.supplier.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class SupplierMenuDto {
  
  SupplierDto supplier;
  List<FoodDto> menu;

}
