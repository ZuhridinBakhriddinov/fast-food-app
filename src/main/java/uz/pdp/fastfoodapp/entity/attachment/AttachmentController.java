package uz.pdp.fastfoodapp.entity.attachment;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

// Zuhridin Bakhriddinov 3/15/2022 12:23 PM
@RestController
@RequestMapping("/getFile")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    @GetMapping("/{id}")
    public void getFile(@PathVariable UUID id, HttpServletResponse response){

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");

                response.setContentType(attachment.getContentType());

                FileCopyUtils.copy(attachmentContent.getData(),response.getOutputStream());
            }

        }


    }

    @GetMapping("/download/{attachmentId}")
    public HttpEntity<?> getAttachmentFile(@PathVariable UUID attachmentId) throws IOException {
        return attachmentService.fileDownload(attachmentId);
    }

}
