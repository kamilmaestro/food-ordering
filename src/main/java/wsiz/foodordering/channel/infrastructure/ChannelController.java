package wsiz.foodordering.channel.infrastructure;

import wsiz.foodordering.channel.domain.ChannelFacade;
import wsiz.foodordering.channel.dto.ChannelDto;
import wsiz.foodordering.channel.dto.ChannelMemberDto;
import wsiz.foodordering.infrastructure.PageInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/channel")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ChannelController {

  ChannelFacade channelFacade;

  @Autowired
  ChannelController(ChannelFacade channelFacade) {
    this.channelFacade = channelFacade;
  }

  @PostMapping(value = "/", consumes = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<ChannelDto> createChannel(@RequestBody String name) {
    return ResponseEntity.ok(channelFacade.createChannel(name));
  }

  @PostMapping("/{channelId}/invitation")
  ResponseEntity<String> generateInvitation(@PathVariable long channelId) {
    return ResponseEntity.ok(channelFacade.generateInvitation(channelId));
  }

  @PostMapping(value = "/join", consumes = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<ChannelMemberDto> joinChannel(@RequestBody String invitation) {
    return ResponseEntity.ok(channelFacade.joinChannel(invitation));
  }

  @GetMapping("/")
  public ResponseEntity<Page<ChannelDto>> findChannelsByUserId(@ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(channelFacade.findChannelsByUserId(pageInfo));
  }

  @GetMapping("/{channelId}/members")
  public ResponseEntity<Collection<ChannelMemberDto>> findChannelMembers(@PathVariable long channelId) {
    return ResponseEntity.ok(channelFacade.findChannelMembers(channelId));
  }

  @PostMapping("/ids")
  public ResponseEntity<Page<ChannelDto>> findChannelsByIds(@RequestBody Collection<Long> channelIds,
                                                            @ModelAttribute PageInfo pageInfo) {
    return ResponseEntity.ok(channelFacade.findChannelsByIds(channelIds, pageInfo));
  }

}
