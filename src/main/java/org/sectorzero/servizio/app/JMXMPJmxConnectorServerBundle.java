package org.sectorzero.servizio.app;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;

import org.sectorzero.servizio.dropwizard.guice.RuntimeBundle;
import io.dropwizard.setup.Environment;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;

import lombok.extern.log4j.Log4j;

@Log4j
public class JMXMPJmxConnectorServerBundle extends RuntimeBundle {

    // Example :
    // System.getProperty("javax.management.remote.JMXServiceURL", "service:jmx:jmxmp://127.0.0.1:59992"));
    static final String JMXServiceURLPropKey = "javax.management.remote.JMXServiceURL";

    @Override
    public void run(Environment environment) {
        String jmxServiceUrlPropVal = System.getProperty(JMXServiceURLPropKey);
        if (StringUtils.isEmpty(jmxServiceUrlPropVal)) {
            log.info(String.format("No JMXMP enabled JMXConnectorServer started, %s System Property Not " +
                "Specified", JMXServiceURLPropKey));
            return;
        }

        try {
            JMXServiceURL jmxUrl = new JMXServiceURL(jmxServiceUrlPropVal);
            Map<String, String> jmxEnvironment = new ImmutableMap.Builder<String, String>()
                .put("jmx.remote.server.address.wildcard", "false")
                .build();
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            JMXConnectorServer jmxRemoteServer = JMXConnectorServerFactory.newJMXConnectorServer(
                jmxUrl, jmxEnvironment, mbs);

            jmxRemoteServer.start();

            log.info(String.format("JMXMP enabled JMXConnectorServer started on Url=%s", jmxServiceUrlPropVal));
        } catch (Exception e) {
            log.error(String.format("Exception starting JMXMP enabled JMXConnectorServer on Url=%s"
                , jmxServiceUrlPropVal), e);
            System.exit(1);
        }
    }
}
