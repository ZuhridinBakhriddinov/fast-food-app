package uz.pdp.fastfoodapp.entity.attachment;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "attachment_contents")
public class AttachmentContent extends AbsEntity {
    @OneToOne(cascade = CascadeType.MERGE)
    Attachment attachment;
    byte [] data;


}
