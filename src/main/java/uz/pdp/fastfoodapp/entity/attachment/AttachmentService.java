package uz.pdp.fastfoodapp.entity.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    public Attachment downloadAndGetImage(MultipartFile file){
        try {
            Attachment image = attachmentRepository.save(new Attachment(file.getOriginalFilename(), file.getContentType(), file.getSize()));
            attachmentContentRepository.save(new AttachmentContent(image, file.getBytes()));
            return image;
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public ResponseEntity<ByteArrayResource> fileDownload(UUID attachmentId) throws IOException{
        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachmentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(optionalAttachmentContent.get().getAttachment().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + optionalAttachmentContent.get().getAttachment().getName() + "\"")
                .body(new ByteArrayResource(optionalAttachmentContent.get().getData()));
    }


}
