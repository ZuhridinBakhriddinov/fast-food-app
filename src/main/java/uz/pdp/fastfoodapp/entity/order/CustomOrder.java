package uz.pdp.fastfoodapp.entity.order;

import java.sql.Timestamp;
import java.util.UUID;

public interface CustomOrder {
    UUID getId();

    Integer getOrderNumber();

    Timestamp getDate();

    String getPayType();

    String getStatus();

    Double getSum();

}
