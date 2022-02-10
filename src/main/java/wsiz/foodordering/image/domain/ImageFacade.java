package wsiz.foodordering.image.domain;

import wsiz.foodordering.image.dto.ImageDto;
import wsiz.foodordering.image.dto.ImageUploadedDto;
import wsiz.foodordering.image.exception.ImageNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageFacade {

  ImageRepository imageRepository;

  public ImageUploadedDto storeImage(MultipartFile file) {
    return imageRepository.save(Image.fromMultiPartFile(file)).toUploadImageResponse(file.getSize());
  }

  public ImageDto getImage(long imageId) {
    return imageRepository.findById(imageId).orElseThrow(ImageNotFound::new).dto();
  }

}
