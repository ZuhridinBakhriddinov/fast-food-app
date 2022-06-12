package uz.pdp.fastfoodapp.entity.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
