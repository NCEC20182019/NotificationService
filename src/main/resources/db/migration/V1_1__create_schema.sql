CREATE TABLE "subscriptions" (
	"sub_type" varchar(5) NOT NULL,
	"id" serial NOT NULL,
	"name" varchar(200) NOT NULL,
	"enabled" BOOLEAN NOT NULL,
	"user_id" int NOT NULL,
	"event_id" int,
	"latitude" float8,
	"longitude" float8,
	"radius" float8,
	"type" varchar(30),
	CONSTRAINT subscriptions_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "templates" (
	"id" serial NOT NULL,
	"text" varchar(500) NOT NULL,
	"subject" varchar(300) NOT NULL,
	CONSTRAINT templates_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);