create TABLE cat (
   cat_id serial PRIMARY KEY,
   catname VARCHAR (50) UNIQUE NOT NULL,
   weight FLOAT (8) NOT NULL
);

insert into cat(catname, weight)
values ('Glafira', 8.6), ('Jerom', 10.5);