package nc.project.controller;

import nc.project.model.Notification;
import nc.project.model.User;
import nc.project.model.dto.NotificationGetDTO;
import nc.project.model.dto.NotificationSubscribeDTO;
import nc.project.repository.NotificationRepository;
import nc.project.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MainController {

    private final ModelMapper modelMapper = new ModelMapper();
    private final NotificationRepository notificationRepo;
    private final NotificationService notificationService;

    @Autowired
    public MainController(NotificationRepository notificationRepo,
                          NotificationService notificationService) {
        this.notificationRepo = notificationRepo;
        this.notificationService = notificationService;
        /*modelMapper.addMappings(new PropertyMap<Notification, NotificationGetDTO>() {
            @Override
            protected void configure() {
                Set<String> emails = new HashSet<>();
                source.getUsers().forEach(u -> emails.add(u.getEmail()));
                map().setEmails(emails);
            }
        });*/
    }

    //TODO код связанный с обработкой данных перенести в сервисный слой
    //TODO написать дто для гет запроса
    @GetMapping("/get-notifications")
    public List<NotificationGetDTO> getAllNotifications(){
        List<Notification> list = new ArrayList<>();
        List<NotificationGetDTO> response = new ArrayList<>();
        Set<String> emails = new HashSet<>();

        notificationRepo.findAll().forEach(list::add);
        list.forEach(x->{
            x.getUsers().forEach(u -> emails.add(u.getEmail()));
            response.add(new NotificationGetDTO(x.getId(),x.getText(), emails));
        });
        return response;
    }

    /**
     * newNotification
     * {
     *   "text": "content",
     *   "emails":[
     *     "email",
     *     "email",
     *     "email"
     *   ]
     * }
     */
    @PostMapping("/subscribe")
    public void subscribe(@RequestBody NotificationSubscribeDTO newNotification){

        notificationService.subscribe(newNotification);
    }

    @GetMapping("/notify")
    public String notifySubscribers(){

        notificationService.startNotify();
        return "Notifications is started";
    }
}
