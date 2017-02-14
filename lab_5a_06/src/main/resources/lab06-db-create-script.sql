--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.1
-- Started on 2017-02-14 16:11:28

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 174 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1954 (class 0 OID 0)
-- Dependencies: 174
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 1123057)
-- Name: employee; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE employee (
    emp_id integer NOT NULL,
    emp_name character varying(60)
);


--
-- TOC entry 170 (class 1259 OID 1123055)
-- Name: employee_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE employee_emp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 1955 (class 0 OID 0)
-- Dependencies: 170
-- Name: employee_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE employee_emp_id_seq OWNED BY employee.emp_id;


--
-- TOC entry 173 (class 1259 OID 1123093)
-- Name: working_hours; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE working_hours (
    wh_id integer NOT NULL,
    wh_emp integer,
    wh_hours numeric,
    wh_date date
);


--
-- TOC entry 172 (class 1259 OID 1123091)
-- Name: working_hours_wh_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE working_hours_wh_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 1956 (class 0 OID 0)
-- Dependencies: 172
-- Name: working_hours_wh_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE working_hours_wh_id_seq OWNED BY working_hours.wh_id;


--
-- TOC entry 1830 (class 2604 OID 1123060)
-- Name: emp_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee ALTER COLUMN emp_id SET DEFAULT nextval('employee_emp_id_seq'::regclass);


--
-- TOC entry 1831 (class 2604 OID 1123096)
-- Name: wh_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY working_hours ALTER COLUMN wh_id SET DEFAULT nextval('working_hours_wh_id_seq'::regclass);


--
-- TOC entry 1945 (class 0 OID 1123057)
-- Dependencies: 171
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO employee VALUES (1, 'John Doe');
INSERT INTO employee VALUES (2, 'Jane Doe');
INSERT INTO employee VALUES (3, 'Marc Smith');


--
-- TOC entry 1957 (class 0 OID 0)
-- Dependencies: 170
-- Name: employee_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('employee_emp_id_seq', 1, false);


--
-- TOC entry 1947 (class 0 OID 1123093)
-- Dependencies: 173
-- Data for Name: working_hours; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO working_hours VALUES (1, 1, 5, '2015-07-17');
INSERT INTO working_hours VALUES (2, 2, 6, '2015-07-17');
INSERT INTO working_hours VALUES (3, 3, 9, '2015-07-17');
INSERT INTO working_hours VALUES (4, 1, 3.5, '2015-07-17');
INSERT INTO working_hours VALUES (5, 3, 6, '2015-07-18');
INSERT INTO working_hours VALUES (6, 2, 8.5, '2015-07-18');
INSERT INTO working_hours VALUES (7, 1, 5.5, '2015-07-18');
INSERT INTO working_hours VALUES (8, 3, 2, '2015-07-18');
INSERT INTO working_hours VALUES (9, 1, 2.5, '2015-07-18');


--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 172
-- Name: working_hours_wh_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('working_hours_wh_id_seq', 1, false);


--
-- TOC entry 1833 (class 2606 OID 1123062)
-- Name: pk_emp; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT pk_emp PRIMARY KEY (emp_id);


--
-- TOC entry 1835 (class 2606 OID 1123101)
-- Name: wh_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY working_hours
    ADD CONSTRAINT wh_pk PRIMARY KEY (wh_id);


--
-- TOC entry 1836 (class 2606 OID 1123102)
-- Name: wh_fk_emp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY working_hours
    ADD CONSTRAINT wh_fk_emp FOREIGN KEY (wh_emp) REFERENCES employee(emp_id);


-- Completed on 2017-02-14 16:11:28

--
-- PostgreSQL database dump complete
--

