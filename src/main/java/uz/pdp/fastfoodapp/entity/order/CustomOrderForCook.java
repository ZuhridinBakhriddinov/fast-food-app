package uz.pdp.fastfoodapp.entity.order;

import java.sql.Timestamp;
import java.util.UUID;

public interface CustomOrderForCook {
    UUID getId();

    Integer getOrderNumber();

    Timestamp getDate();

    String getOrderItems();

}
