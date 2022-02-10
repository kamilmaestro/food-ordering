package wsiz.foodordering.image.infrastructure;

import wsiz.foodordering.image.domain.ImageFacade;
import wsiz.foodordering.image.dto.ImageDto;
import wsiz.foodordering.image.dto.ImageUploadedDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ImageController {

  ImageFacade imageFacade;

  @Autowired
  public ImageController(@Autowired ImageFacade imageFacade) {
    this.imageFacade = imageFacade;
  }

  @CrossOrigin
  @PostMapping("/")
  public ResponseEntity<ImageUploadedDto> uploadImage(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(imageFacade.storeImage(file));
  }

  @CrossOrigin
  @GetMapping("/{imageId}")
  public ResponseEntity<Resource> downloadFile(@PathVariable long imageId) {
    final ImageDto image = imageFacade.getImage(imageId);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(image.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName() + "")
        .body(new ByteArrayResource(image.getData()));
  }

}
