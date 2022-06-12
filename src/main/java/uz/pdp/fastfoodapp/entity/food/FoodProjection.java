package uz.pdp.fastfoodapp.entity.food;

import java.util.UUID;

public interface FoodProjection {

    UUID getFoodId();

    String getName();

    Double getPrice();

    UUID getImageId();

    String getCategoryName();

}
