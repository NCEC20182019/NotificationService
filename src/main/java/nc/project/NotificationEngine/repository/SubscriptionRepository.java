package nc.project.NotificationEngine.repository;

import nc.project.NotificationEngine.model.Subscription.EventSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query(value = "select s.* from subscriptions s where (getDistance(s.latitude, s.longitude, :latitude, :longitude) <= s.radius) or s.type = :type)", nativeQuery = true)
    List<Subscription> findAreaAndTypeSubscriptions(@Param("latitude") double latitude,
                                                    @Param("longitude") double longitude,
                                                    @Param("type") String type);

    @Query(value = "select s from Subscription s where s.eventId = :id")
    List<EventSubscription> findEventSubscription(@Param("id") int id);

    List<Subscription> findAllByUserId(int id);

    void deleteByIdIn(int[] ids);

    @Query(value = "delete from subscriptions s where s.user_id = :userId and s.event_id = :eventId", nativeQuery = true)
    @Modifying
    void deleteEventSubscription(@Param("userId") int userId,
                                 @Param("eventId") int eventId);

    @Query(value = "select count(s.*) from subscriptions s where s.user_id = :userId and s.event_id = :eventId", nativeQuery = true)
    int findSubscription(@Param("userId") int userId,
                         @Param("eventId") int eventId);
}