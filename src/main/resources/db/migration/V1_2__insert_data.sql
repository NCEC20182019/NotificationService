insert into subscriptions (sub_type, name, enabled, user_id, latitude, longitude, radius)
  values ('area','sub1', true, 1, 55.1, 26.7, 3);
insert into subscriptions (sub_type, name, enabled, user_id, latitude, longitude, radius)
values ('area','sub2', true, 1, 53.6, 26.7, 1);

insert into subscriptions (sub_type, name, enabled, user_id, event_id)
values ('event','sub3', true, 1, 1);

insert into subscriptions (sub_type, name, enabled, user_id, type)
values ('type','sub4', true, 1, 'cinema');

insert into templates (subject, text) values ('Notification from lemmeknow.tk','Hello, %s!\nPlaceholder text!!!! Your subscription %s!');