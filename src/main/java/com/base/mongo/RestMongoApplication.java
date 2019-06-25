package com.base.mongo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * Main class for application startup.
 *
 * @author vleon
 */
@SpringBootApplication
@ComponentScan(basePackages="com.base")
public class RestMongoApplication {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RestMongoApplication.class);

	private final Environment env;
	
	 /**
     * Inyección de dependencias por constructor
     *
     * @param env
     */
    public RestMongoApplication(Environment env) {
        this.env = env;
    }
    
    /**
     * Inicializa la aplicación.
     * <p>
     * Los profiles de Spring pueden ser especificados como argumentos de línea de
     * comandos: --spring.profiles.active=el-profile-activo
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        LOGGER.info("activeProfiles: " + activeProfiles.toString());
    }

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(RestMongoApplication.class);
        String protocol = "http";
        Environment env = app.run(args).getEnvironment();
		LOGGER.info("\n----------------------------------------------------------\n\t" +
                "Aplicacion '{}' en ejecucion. URLs de acceso:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "Externa: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());	
	}

}
