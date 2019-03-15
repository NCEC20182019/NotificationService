CREATE TABLE "notifications" (
	"notification_id" serial NOT NULL,
	"notification_text" varchar(200) NOT NULL,
	CONSTRAINT notifications_pk PRIMARY KEY ("notification_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "users" (
	"user_id" serial NOT NULL,
	"email" varchar(120) NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY ("user_id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_notification" (
	"notification_id" int NOT NULL,
	"user_id" int NOT NULL,
	CONSTRAINT user_notification_pk PRIMARY KEY ("notification_id","user_id")
) WITH (
  OIDS=FALSE
);





ALTER TABLE "user_notification" ADD CONSTRAINT "user_notification_fk0" FOREIGN KEY ("notification_id") REFERENCES "notifications"("notification_id");
ALTER TABLE "user_notification" ADD CONSTRAINT "user_notification_fk1" FOREIGN KEY ("user_id") REFERENCES "users"("user_id");
