PGDMP         4            
    y            fafij    13.3    13.3 3    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16394    fafij    DATABASE     b   CREATE DATABASE fafij WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE fafij;
                postgres    false            �            1259    16966    category    TABLE     �   CREATE TABLE public.category (
    id integer NOT NULL,
    name text DEFAULT ''::text NOT NULL,
    id_jrnl bigint NOT NULL
);
    DROP TABLE public.category;
       public         heap    postgres    false            �            1259    16964    category_id_seq    SEQUENCE     �   CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.category_id_seq;
       public          postgres    false    208            �           0    0    category_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;
          public          postgres    false    207            �            1259    16932    journal    TABLE     i   CREATE TABLE public.journal (
    id integer NOT NULL,
    name text DEFAULT 'journal'::text NOT NULL
);
    DROP TABLE public.journal;
       public         heap    postgres    false            �            1259    16930    journal_id_seq    SEQUENCE     �   CREATE SEQUENCE public.journal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.journal_id_seq;
       public          postgres    false    205            �           0    0    journal_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.journal_id_seq OWNED BY public.journal.id;
          public          postgres    false    204            �            1259    16983    note    TABLE     �   CREATE TABLE public.note (
    id integer NOT NULL,
    ctgr bigint NOT NULL,
    comment text,
    id_jrnl bigint NOT NULL,
    sum integer DEFAULT 0 NOT NULL,
    date text
);
    DROP TABLE public.note;
       public         heap    postgres    false            �            1259    16981    note_id_seq    SEQUENCE     �   CREATE SEQUENCE public.note_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.note_id_seq;
       public          postgres    false    210            �           0    0    note_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.note_id_seq OWNED BY public.note.id;
          public          postgres    false    209            �            1259    16633    roles    TABLE     e   CREATE TABLE public.roles (
    id integer NOT NULL,
    role_name character varying(30) NOT NULL
);
    DROP TABLE public.roles;
       public         heap    postgres    false            �            1259    16631    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    201            �           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    200            �            1259    16944 
   user_roles    TABLE     z   CREATE TABLE public.user_roles (
    id_user bigint NOT NULL,
    id_jrnl bigint NOT NULL,
    id_role bigint NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            �            1259    16917    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    password text NOT NULL,
    login text DEFAULT 'user'::text NOT NULL,
    CONSTRAINT user_password_check CHECK ((length(password) >= 6))
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16915    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    203            �           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    202            H           2604    16969    category id    DEFAULT     j   ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);
 :   ALTER TABLE public.category ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    207    208    208            F           2604    16935 
   journal id    DEFAULT     h   ALTER TABLE ONLY public.journal ALTER COLUMN id SET DEFAULT nextval('public.journal_id_seq'::regclass);
 9   ALTER TABLE public.journal ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    205    205            J           2604    16986    note id    DEFAULT     b   ALTER TABLE ONLY public.note ALTER COLUMN id SET DEFAULT nextval('public.note_id_seq'::regclass);
 6   ALTER TABLE public.note ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209    210            B           2604    16636    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    200    201    201            C           2604    16920    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    202    203    203            �          0    16966    category 
   TABLE DATA           5   COPY public.category (id, name, id_jrnl) FROM stdin;
    public          postgres    false    208   �6       �          0    16932    journal 
   TABLE DATA           +   COPY public.journal (id, name) FROM stdin;
    public          postgres    false    205   �6       �          0    16983    note 
   TABLE DATA           E   COPY public.note (id, ctgr, comment, id_jrnl, sum, date) FROM stdin;
    public          postgres    false    210   "7       �          0    16633    roles 
   TABLE DATA           .   COPY public.roles (id, role_name) FROM stdin;
    public          postgres    false    201   ?7       �          0    16944 
   user_roles 
   TABLE DATA           ?   COPY public.user_roles (id_user, id_jrnl, id_role) FROM stdin;
    public          postgres    false    206   p7       �          0    16917    users 
   TABLE DATA           4   COPY public.users (id, password, login) FROM stdin;
    public          postgres    false    203   �7       �           0    0    category_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.category_id_seq', 3, true);
          public          postgres    false    207            �           0    0    journal_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.journal_id_seq', 3, true);
          public          postgres    false    204            �           0    0    note_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.note_id_seq', 1, false);
          public          postgres    false    209            �           0    0    roles_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.roles_id_seq', 3, true);
          public          postgres    false    200                        0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 3, true);
          public          postgres    false    202            [           2606    16975    category category_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pk;
       public            postgres    false    208            U           2606    16943    journal journal_name_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_name_key UNIQUE (name);
 B   ALTER TABLE ONLY public.journal DROP CONSTRAINT journal_name_key;
       public            postgres    false    205            W           2606    16941    journal journal_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.journal
    ADD CONSTRAINT journal_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.journal DROP CONSTRAINT journal_pk;
       public            postgres    false    205            ]           2606    16992    note note_pk 
   CONSTRAINT     J   ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_pk PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.note DROP CONSTRAINT note_pk;
       public            postgres    false    210            M           2606    16638    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    201            O           2606    16640    roles roles_role_name_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_role_name_key UNIQUE (role_name);
 C   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_role_name_key;
       public            postgres    false    201            Q           2606    16929    users user_login_key 
   CONSTRAINT     P   ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_login_key UNIQUE (login);
 >   ALTER TABLE ONLY public.users DROP CONSTRAINT user_login_key;
       public            postgres    false    203            S           2606    16927    users user_pk 
   CONSTRAINT     K   ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pk PRIMARY KEY (id);
 7   ALTER TABLE ONLY public.users DROP CONSTRAINT user_pk;
       public            postgres    false    203            Y           2606    16948    user_roles user_roles_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id_user, id_jrnl, id_role);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    206    206    206            a           2606    16976    category category_id_jrnl_fkey    FK CONSTRAINT        ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_id_jrnl_fkey FOREIGN KEY (id_jrnl) REFERENCES public.journal(id);
 H   ALTER TABLE ONLY public.category DROP CONSTRAINT category_id_jrnl_fkey;
       public          postgres    false    2903    208    205            b           2606    16993    note note_ctgr_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_ctgr_fkey FOREIGN KEY (ctgr) REFERENCES public.category(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 =   ALTER TABLE ONLY public.note DROP CONSTRAINT note_ctgr_fkey;
       public          postgres    false    208    210    2907            c           2606    16998    note note_id_jrnl_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.note
    ADD CONSTRAINT note_id_jrnl_fkey FOREIGN KEY (id_jrnl) REFERENCES public.journal(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 @   ALTER TABLE ONLY public.note DROP CONSTRAINT note_id_jrnl_fkey;
       public          postgres    false    205    2903    210            _           2606    16954    user_roles role_id_jrnl_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT role_id_jrnl_fkey FOREIGN KEY (id_jrnl) REFERENCES public.journal(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 F   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT role_id_jrnl_fkey;
       public          postgres    false    2903    206    205            `           2606    16959    user_roles role_id_role_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT role_id_role_fkey FOREIGN KEY (id_role) REFERENCES public.roles(id);
 F   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT role_id_role_fkey;
       public          postgres    false    201    206    2893            ^           2606    16949    user_roles role_login_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT role_login_fkey FOREIGN KEY (id_user) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE RESTRICT;
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT role_login_fkey;
       public          postgres    false    203    206    2899            �   !   x�3�L/�ON-��4�2�LN,�4����� X��      �       x�3�LJ,I����2����aL#�=... �

l      �      x������ � �      �   !   x�3�tt����2ҡ>!\Ɯޞ.\1z\\\ N^3      �   &   x�3�4�4�2�4�4�bNc.cNc������� J�"      �   %   x�3�LJ,I��̃�\F0�!��ec�%��b���� �b{     