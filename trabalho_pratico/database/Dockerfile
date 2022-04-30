FROM postgres:9.6
ENV PG_MAX_WAL_SENDERS 8
ENV PG_WAL_KEEP_SEGMENTS 8
# ARG POSTGRES_INITDB
RUN apt-get update
RUN apt-get install iputils-ping -y
# RUN apt install sed -y
# ADD ./init.sql /docker-entrypoint-initdb.d
# RUN sed -i s/POSTGRES_INITDB/$POSTGRES_INITDB/g /docker-entrypoint-initdb.d/init.sql
COPY setup-replication.sh /docker-entrypoint-initdb.d/
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint-initdb.d/setup-replication.sh /docker-entrypoint.sh
EXPOSE 5432