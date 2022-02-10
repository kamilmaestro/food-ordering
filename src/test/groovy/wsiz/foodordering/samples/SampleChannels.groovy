package wsiz.foodordering.samples

import wsiz.foodordering.channel.dto.AddChannelDto

trait SampleChannels {

  AddChannelDto KRAKOW = AddChannelDto.builder().name("Krakow").build()
  AddChannelDto WARSAW = AddChannelDto.builder().name("Warsaw").build()

}