package wsiz.foodordering.supplier.domain;

import wsiz.foodordering.supplier.dto.FoodDto;
import wsiz.foodordering.supplier.dto.SupplierDto;
import wsiz.foodordering.supplier.dto.SupplierMenuDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "suppliers")
class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "phone_number")
  String phoneNumber;

  @Column(name = "address")
  String address;

  @Column(name = "image_id")
  Long imageId;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  SupplierDto dto() {
    return SupplierDto.builder()
        .id(this.id)
        .name(this.name)
        .phoneNumber(this.phoneNumber)
        .address(this.address)
        .imageId(this.imageId)
        .createdAt(this.createdAt)
        .build();
  }

  SupplierMenuDto menuDto(List<Food> supplierFood) {
    final List<FoodDto> menu = supplierFood.stream()
        .map(Food::dto)
        .collect(Collectors.toList());

    return SupplierMenuDto.builder()
        .supplier(dto())
        .menu(menu)
        .build();
  }

}
