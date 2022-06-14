package uz.pdp.fastfoodapp.entity.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class FeedbackDto  {
    UUID orderId;
    String text;
    Integer rate;
}
