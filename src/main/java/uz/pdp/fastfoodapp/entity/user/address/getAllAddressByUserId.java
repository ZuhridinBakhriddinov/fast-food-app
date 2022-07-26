package uz.pdp.fastfoodapp.entity.user.address;

import java.util.UUID;

public interface getAllAddressByUserId {
    UUID getId();
    String getName();
    String getDistrictName();
    String getLandmark();
    Integer getHouseNumber();
    Integer getEntrance();
    Integer getFlat();
    Integer getFloor();
}
