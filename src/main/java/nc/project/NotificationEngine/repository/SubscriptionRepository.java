package nc.project.NotificationEngine.repository;

import nc.project.NotificationEngine.model.Subscription.AreaSubscription;
import nc.project.NotificationEngine.model.Subscription.EventSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.dto.SubscriptionDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query(value = "select s.* from subscriptions s where (point(:latitude,:longitude) <@ circle(point(s.latitude,s.longitude),s.radius) or s.type = :type)", nativeQuery = true)
    List<Subscription> findAreaAndTypeSubscriptions(@Param("latitude") double latitude,
                                                    @Param("longitude") double longitude,
                                                    @Param("type") String type);

    @Query(value = "select s from Subscription s where s.eventId = :id")
    List<EventSubscription> findEventSubscription(@Param("id") int id);

    List<Subscription> findAllByUserId(int id);

    void deleteByIdIn(int[] ids);

    @Query(value  = "update Subscription set enabled = :isEnable where id = :id")
    @Modifying(clearAutomatically = true)
    void toggleById(@Param("isEnable") boolean isEnable, @Param("id") int id);

//    @Query(value  = "update Subscription " +
//            "set radius = :radius where id = :id")
//    @Modifying(clearAutomatically = true)
//    void updateAreaSubscription(@Param("id") int id,
//                                @Param("lng") int lng,
//                                @Param("ltd") int ltd,
//                                @Param("radius") int radius);

//    @Query("select s.user_id from subscriptions s where s.id = :id and s.user_id = :userId")
//    List<Integer> findAllByIdAndUserId(@Param("id")int id, @Param("userId")int userId);
}