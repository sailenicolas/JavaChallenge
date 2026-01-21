--
-- PostgreSQL database dump
--

\restrict kvn04C1aLJ4x8hJk1mms1vuAfywwzmW8ZLYeThOVayZ2QPPznDDwHBFqkTfXHNJ

-- Dumped from database version 18.1 (Debian 18.1-1.pgdg13+2)
-- Dumped by pg_dump version 18.1 (Debian 18.1-1.pgdg13+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: credits_model; Type: TABLE; Schema: public; Owner: Usernam22d
--

CREATE TABLE  IF NOT EXISTS public.credits_model (
                                      created_at timestamp(6) without time zone,
                                      amount character varying(255),
                                      id character varying(255) NOT NULL,
                                      point_id character varying(255),
                                      point_name character varying(255)
);


ALTER TABLE IF EXISTS public.credits_model OWNER TO "POSTGRESQL_OWNER";

--
-- Name: credits_model credits_model_pkey; Type: CONSTRAINT; Schema: public; Owner: Usernam22d
--

ALTER TABLE IF EXISTS ONLY public.credits_model
    ADD CONSTRAINT credits_model_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

\unrestrict kvn04C1aLJ4x8hJk1mms1vuAfywwzmW8ZLYeThOVayZ2QPPznDDwHBFqkTfXHNJ