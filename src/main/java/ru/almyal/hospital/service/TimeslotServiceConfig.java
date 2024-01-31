package ru.almyal.hospital.service;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class TimeslotServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/allService/*");
    }

    @Bean(name = "timeslots")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema timeslotSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("allServiceSoapHttp");
        wsdl11Definition.setLocationUri("/allService");      // "http://localhost:8080/allService/timeslots.wsdl"
        wsdl11Definition.setTargetNamespace("ru.almyal.hospital.soap");
        wsdl11Definition.setSchema(timeslotSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema timeslotSchema() {
        return new SimpleXsdSchema(new ClassPathResource("timeslots.xsd"));
    }
}
