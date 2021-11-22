package ru.itis.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.itis.repositories.UserRepository;
import ru.itis.repositories.impl.UserRepositoryJdbcImpl;
import ru.itis.services.SignInService;
import ru.itis.services.SignUpService;
import ru.itis.services.impl.SignInServiceImpl;
import ru.itis.services.impl.SignUpServiceImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan("ru.itis")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setUsername(environment.getProperty("db.user"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("db.hikari.pool-size")));
        return hikariConfig;
    }

    @Bean
    public UserRepository userRepository(DataSource dataSource) {
        return new UserRepositoryJdbcImpl(dataSource);
    }

    @Bean
    public SignUpService signUpService(UserRepository userRepository) {
        return new SignUpServiceImpl(userRepository);
    }

    @Bean
    public SignInService signInService(UserRepository userRepository) {
        return new SignInServiceImpl(userRepository);
    }
}

