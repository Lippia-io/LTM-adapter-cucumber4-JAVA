FROM crowdar/lippia_spv:3.2.1.1

USER root

ARG WORKSPACE="/opt/workspace/adapter"

RUN mkdir -p ${WORKSPACE}
    
COPY . ${WORKSPACE}

WORKDIR ${WORKSPACE}

RUN mvn -B verify
RUN mvn -B install

WORKDIR /

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"] 
 
