package uz.pdp.fastfoodapp.entity.user.address;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query(nativeQuery = true, value = "select " +
            "       a.name         as name,\n" +
            "       d.name         as districtName,\n" +
            "       a.landmark     as landmark,\n" +
            "       a.house_number as houseNumber,\n" +
            "       a.entrance     as entrance,\n" +
            "       a.flat         as flat,\n" +
            "       a.floor        as floor\n" +
            "from addresses a\n" +
            "         join district d on d.id = a.district_id\n" +
            "where a.user_id =:userId")
    List<getAllAddressByUserId> findByUserId(UUID userId);
}
