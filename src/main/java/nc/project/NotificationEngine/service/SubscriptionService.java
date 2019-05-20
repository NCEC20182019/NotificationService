package nc.project.NotificationEngine.service;

import nc.project.NotificationEngine.model.Subscription.AreaSubscription;
import nc.project.NotificationEngine.model.Subscription.EventSubscription;
import nc.project.NotificationEngine.model.Subscription.Subscription;
import nc.project.NotificationEngine.model.Subscription.TypeSubscription;
import nc.project.NotificationEngine.model.dto.SubscriptionDTO;
import nc.project.NotificationEngine.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
        modelMapper.addMappings(new PropertyMap<SubscriptionDTO, Subscription>() {
            protected void configure() {
                map().setUserId(source.getUserId());
            }
        });
    }

    public List<Subscription> findAll(int userId) {
        return subscriptionRepository.findAllByUserId(userId);
    }

    public void subscribeOrUpdate(SubscriptionDTO newSubscription) {
        // radius в метрах
        Subscription sub;
        if (newSubscription.checkArea()) {
            sub = modelMapper.map(newSubscription, AreaSubscription.class);
            subscriptionRepository.save(sub);
        }
        if (newSubscription.getType() != null) {
            sub = modelMapper.map(newSubscription, TypeSubscription.class);
            subscriptionRepository.save(sub);
        }
        if (newSubscription.getEventId() != 0) {
            sub = modelMapper.map(newSubscription, EventSubscription.class);
            subscriptionRepository.save(sub);
        }
    }

    @Transactional
    public void unsubscribe(int[] ids) {
        subscriptionRepository.deleteByIdIn(ids);
    }

    @Transactional
    public void unsubscribe(int userId, int eventId) {
        subscriptionRepository.deleteEventSubscription(userId, eventId);
    }

    public void unsubscribe(int id) {
        subscriptionRepository.deleteById(id);
    }

    public boolean checkSubscription(int userId, int eventId) {
        return subscriptionRepository.findSubscription(userId, eventId) > 0;
    }
}
