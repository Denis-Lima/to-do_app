do
$setup_database$
begin
	create sequence if not exists hibernate_sequence;

	create table if not exists "user" (
	    use_cod bigint not null generated by default as identity,
	    use_username varchar not null,
	    use_email varchar not null,
		use_password varchar not null,
	    constraint user_use_cod_pkey primary key (use_cod),
	    constraint user_use_username_ukey unique (use_username),
	    constraint user_use_email_ukey unique (use_email)
	);

	create table if not exists project (
		pro_cod bigint not null generated by default as identity,
		pro_name varchar not null,
		pro_user_cod bigint not null,
		pro_created_at timestamp not null default now(),
	    constraint project_pro_cod_pkey primary key (pro_cod),
	    constraint project_tas_use_cod_ukey foreign key (pro_user_cod) references "user"(use_cod)
	);

	create table if not exists task (
	    tas_cod bigint not null generated by default as identity,
	    tas_title varchar not null,
	    tas_description varchar null,
	    tas_created_at timestamp not null default now(),
	    tas_updated_at timestamp not null,
		tas_finished boolean not null default false,
		tas_project_cod bigint not null,
	    constraint task_tas_cod_pkey primary key (tas_cod),
	    constraint task_tas_project_cod_ukey foreign key (tas_project_cod) references project(pro_cod)
	);

	if exists (select * from pg_catalog.pg_roles where rolname = 'todo_user') then
		reassign owned by todo_user to postgres;
		drop owned by todo_user;
		drop user todo_user;
	end if;

	create user todo_user with encrypted password 'wspsJJ@6h2/Tw293' 
		nosuperuser inherit nocreatedb nocreaterole noreplication valid until 'infinity';
 	grant connect on database todoapp to todo_user;
	grant usage on schema public to todo_user;
	grant select, insert, update, delete on all tables in schema public to todo_user;
	grant usage, select on all sequences in schema public to todo_user;
end
$setup_database$;
