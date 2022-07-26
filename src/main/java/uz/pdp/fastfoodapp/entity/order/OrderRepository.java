package uz.pdp.fastfoodapp.entity.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.fastfoodapp.entity.order.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByUserIdAndStatus(UUID user_id, OrderStatus status);

    @Query(nativeQuery = true, value = "select cast(mo.id  as varchar)         as id,\n" +
            "       mo.order_number as orderNumber,\n" +
            "       mo.created_at as date,\n" +
            "       p.name        as payType,\n" +
            "       mo.status     as status,\n" +
            "       mo.total_sum  as sum\n" +
            "from my_orders mo\n" +
            "         join payments p on mo.payments_id = p.id\n" +
            "where (mo.status = 'PENDING' or mo.status = 'ACCEPTED' or\n" +
            "       mo.status = 'PREPARING' or mo.status = 'READY' or\n" +
            "       mo.status = 'ON_THE_WAY'\n" +
            "    )\n" +
            "  AND mo.user_id = :user_id\n" +
            "  AND lower(mo.status) like lower(concat('%', :search, '%'))")
    Page<CustomOrder> findAllRecentlyMyOrder(Pageable pageable, String search, UUID user_id);


    @Query(nativeQuery = true, value = "select cast(mo.id  as varchar)         as id,\n" +
            "                   mo.order_number as orderNumber,\n" +
            "                   mo.created_at as date,\n" +
            "                   p.name        as payType,\n" +
            "                   mo.status     as status,\n" +
            "                   mo.total_sum  as sum\n" +
            "            from my_orders mo\n" +
            "                     join payments p on mo.payments_id = p.id\n" +
            "            where (mo.status = 'COMPLETED' or mo.status = 'CANCELLED'\n" +
            "                )\n" +
            "              AND mo.user_id = :user_id\n" +
            "              AND lower(mo.status) like lower(concat('%', :search, '%'))")
    Page<CustomOrder> findAllOrderHistory(Pageable pageable, String search, UUID user_id);


    @Query(nativeQuery = true, value = "select  cast(mo.id as varchar) as id,\n" +
            "        mo.order_number        as orderNumber,\n" +
            "        mo.created_at          as date,\n" +
            "        json_agg(oi.quantity||' x '|| f.name_uz  ) as orderItems\n" +
            "\n" +
            "from my_orders mo\n" +
            "         join order_items oi on mo.id = oi.order_id\n" +
            "         join foods f on f.id = oi.food_id\n" +
            "where (mo.status = 'ACCEPTED')\n" +
            "  AND lower(mo.status) like lower(concat('%', :search, '%'))\n" +
            "    group by mo.id,mo.created_at\n" +
            "order by mo.created_at")
    Page<CustomOrderForCook> findAcceptedOrderForCook(Pageable pageable, String search);


    @Query(nativeQuery = true, value = "select  cast(mo.id as varchar) as id,\n" +
            "        mo.order_number        as orderNumber,\n" +
            "        mo.created_at          as date,\n" +
            "        json_agg(oi.quantity||' x '|| f.name_uz  ) as orderItems\n" +
            "\n" +
            "from my_orders mo\n" +
            "         join order_items oi on mo.id = oi.order_id\n" +
            "         join foods f on f.id = oi.food_id\n" +
            "where (mo.status = 'PREPARING')\n" +
            "  AND lower(mo.status) like lower(concat('%', :search, '%'))\n" +
            "    group by mo.id,mo.created_at\n" +
            "order by mo.created_at")
    Page<CustomOrderForCook> findPreparedOrderForCook(Pageable pageable, String search);


}
