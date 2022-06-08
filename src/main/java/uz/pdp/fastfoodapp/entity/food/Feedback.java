package uz.pdp.fastfoodapp.entity.food;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.pdp.fastfoodapp.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@PackagePrivate
@Entity(name = "feedbacks")
public class Feedback extends AbsEntity {
    @OneToOne
    @JoinColumn(name = "order_id")
    Order order;
    String text;
    Integer rate;
}
