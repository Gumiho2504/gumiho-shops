create table category (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB

 create table image (
        id bigint not null auto_increment,
        download_url varchar(255),
        file_name varchar(255),
        file_type varchar(255),
        image longblob,
        product_id bigint,
        primary key (id)
    ) engine=InnoDB

 create table product (
        id bigint not null auto_increment,
        brand varchar(255),
        description varchar(255),
        inventory integer not null,
        name varchar(255),
        price decimal(38,2),
        category_id bigint,
        primary key (id)
    ) engine=InnoDB  

    alter table image 
       add constraint FKgpextbyee3uk9u6o2381m7ft1 
       foreign key (product_id) 
       references product (id)

     alter table product 
       add constraint FK1mtsbur82frn64de7balymq9s 
       foreign key (category_id) 
       references category (id)