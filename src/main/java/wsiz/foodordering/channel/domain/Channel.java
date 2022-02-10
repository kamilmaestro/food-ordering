package wsiz.foodordering.channel.domain;

import wsiz.foodordering.channel.dto.ChannelDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static wsiz.foodordering.infrastructure.authentication.LoggedUserGetter.getLoggedUserId;
import static wsiz.foodordering.infrastructure.authentication.LoggedUserGetter.getLoggedUsername;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "channels")
class Channel {

  @Setter(value = AccessLevel.PACKAGE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Embedded
  @AttributeOverride(name = "name", column = @Column(name = "name"))
  ChannelName name;

  @Column(name = "uuid")
  String uuid;

  @Column(name = "created_by")
  Long createdBy;

  ChannelDto dto() {
    return ChannelDto.builder()
        .id(this.id)
        .name(this.name.getValue())
        .createdBy(this.createdBy)
        .build();
  }

  ChannelMember join() {
    return new ChannelMember(this.id, getLoggedUserId(), getLoggedUsername());
  }

}
