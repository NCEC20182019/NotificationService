package nc.project.NotificationEngine.repository;

import nc.project.NotificationEngine.model.Subscription.EventSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query(value = "select s.* from subscriptions s where (point(:latitude,:longitude) <@ circle(point(s.latitude,s.longitude),s.radius) or s.type = :type)", nativeQuery = true)
    List<Subscription> findAreaAndTypeSubscriptions(@Param("latitude") double latitude,
                                                    @Param("longitude") double longitude,
                                                    @Param("type") String type);

    @Query(value = "select s from Subscription s where s.eventId = :id")
    List<EventSubscription> findEventSubscription(@Param("id") int id);

//    @Query("select s.user_id from subscriptions s where s.id = :id and s.user_id = :userId")
//    List<Integer> findAllByIdAndUserId(@Param("id")int id, @Param("userId")int userId);
}