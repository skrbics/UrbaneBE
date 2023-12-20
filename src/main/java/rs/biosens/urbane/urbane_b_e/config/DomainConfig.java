package rs.biosens.urbane.urbane_b_e.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("rs.biosens.urbane.urbane_b_e.domain")
@EnableJpaRepositories("rs.biosens.urbane.urbane_b_e.repos")
@EnableTransactionManagement
public class DomainConfig {
}
