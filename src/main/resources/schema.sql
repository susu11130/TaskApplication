CREATE TABLE IF NOT EXISTS public.tasks
(
    id integer NOT NULL,
    title character varying(50) COLLATE pg_catalog."default" NOT NULL,
    category character varying(50) COLLATE pg_catalog."default",
    duedate date,
    adddate timestamp(6) without time zone,
    upddate timestamp(6) without time zone,
    version integer,
    CONSTRAINT tasks_pkey PRIMARY KEY (id)
);
