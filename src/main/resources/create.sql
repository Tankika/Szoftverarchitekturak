CREATE SEQUENCE public.authority_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 8
  CACHE 1;
ALTER TABLE public.authority_sequence
  OWNER TO postgres;

CREATE SEQUENCE public.issue_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE public.issue_sequence
  OWNER TO postgres;
  
CREATE SEQUENCE public.project_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2
  CACHE 1;
ALTER TABLE public.project_sequence
  OWNER TO postgres;

CREATE SEQUENCE public.role_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 4
  CACHE 1;
ALTER TABLE public.role_sequence
  OWNER TO postgres;
  
CREATE SEQUENCE public.user_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 4
  CACHE 1;
ALTER TABLE public.user_sequence
  OWNER TO postgres;
  
CREATE TABLE public.authority
(
  id bigint NOT NULL,
  authority character varying(255) NOT NULL,
  CONSTRAINT authority_pkey PRIMARY KEY (id),
  CONSTRAINT uk_nrgoi6sdvipfsloa7ykxwlslf UNIQUE (authority)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.authority
  OWNER TO postgres;
  
CREATE TABLE public.role
(
  id bigint NOT NULL,
  role_name character varying(255),
  CONSTRAINT role_pkey PRIMARY KEY (id),
  CONSTRAINT uk_iubw515ff0ugtm28p8g3myt0h UNIQUE (role_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.role
  OWNER TO postgres;
  
CREATE TABLE public.role_auth
(
  role_id bigint NOT NULL,
  auth_id bigint NOT NULL,
  CONSTRAINT role_auth_pkey PRIMARY KEY (auth_id, role_id),
  CONSTRAINT fk_o4jpba3le29b0oxp8df0tr8sl FOREIGN KEY (role_id)
      REFERENCES public.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_s8liweuonnk05fdesyp6ts1lg FOREIGN KEY (auth_id)
      REFERENCES public.authority (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.role_auth
  OWNER TO postgres;
  
CREATE TABLE public.users
(
  id bigint NOT NULL,
  email character varying(254) NOT NULL,
  enabled boolean NOT NULL,
  password character varying(100) NOT NULL,
  role_id bigint,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT fk_krvotbtiqhudlkamvlpaqus0t FOREIGN KEY (role_id)
      REFERENCES public.role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users
  OWNER TO postgres;
  
CREATE TABLE public.project
(
  id bigint NOT NULL,
  description character varying(255),
  name character varying(255),
  CONSTRAINT project_pkey PRIMARY KEY (id),
  CONSTRAINT uk_3k75vvu7mevyvvb5may5lj8k7 UNIQUE (name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.project
  OWNER TO postgres;
  
CREATE TABLE public.users_projects
(
  users_id bigint NOT NULL,
  projects_id bigint NOT NULL,
  CONSTRAINT users_projects_pkey PRIMARY KEY (users_id, projects_id),
  CONSTRAINT fk_orvjtncj5ebwoa33cwvkwurre FOREIGN KEY (projects_id)
      REFERENCES public.project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_taqjpax0avs66nhenlnm1rt97 FOREIGN KEY (users_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.users_projects
  OWNER TO postgres;
  
CREATE TABLE public.issue
(
  id bigint NOT NULL,
  creation_time_stamp timestamp without time zone,
  description character varying(255),
  name character varying(255),
  priority integer,
  reproduction_steps character varying(255),
  severity integer,
  status integer,
  type integer,
  version character varying(255),
  assignee_id bigint,
  project_id bigint NOT NULL,
  CONSTRAINT issue_pkey PRIMARY KEY (id),
  CONSTRAINT fk_13ifiqjxslqe7ess5di7ytpv0 FOREIGN KEY (assignee_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_73kl4d88gc5ct5iq6blo13ow4 FOREIGN KEY (project_id)
      REFERENCES public.project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_b30v7la90i14aim7immn55lx2 UNIQUE (project_id, name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.issue
  OWNER TO postgres;

CREATE TABLE public.comment
(
  id bigint NOT NULL,
  message character varying(255),
  time_stamp timestamp without time zone,
  author_id bigint NOT NULL,
  issue_id bigint NOT NULL,
  CONSTRAINT comment_pkey PRIMARY KEY (id),
  CONSTRAINT fk_9aq5p2jgf17y6b38x5ayd90oc FOREIGN KEY (author_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_d3o6nr3tysxfov49nefwjlwx7 FOREIGN KEY (issue_id)
      REFERENCES public.issue (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.comment
  OWNER TO postgres;