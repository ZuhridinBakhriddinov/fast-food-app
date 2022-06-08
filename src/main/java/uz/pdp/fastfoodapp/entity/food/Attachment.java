package uz.pdp.fastfoodapp.entity.food;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "attachments")
public class Attachment extends AbsEntity {
    String name;
    String contentType;
    Long size;

}
