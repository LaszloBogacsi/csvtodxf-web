select count(*) from pg_catalog.pg_database where datname = 'csvtodxf-prod';

CREATE TABLE IF NOT EXISTS public.convert_task
(
  id bigint NOT NULL,
  download_id character varying(255),
  download_path character varying(255),
  job_id character varying(255),
  duration_in_millies bigint NOT NULL,
  file_size double precision NOT NULL,
  number_of_lines_converted integer NOT NULL,
  saved_file_path character varying(255),
  result character varying(255),
  CONSTRAINT convert_task_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.convert_task
  OWNER TO csvtodxf;