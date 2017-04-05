import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by cv on 4/5/17.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.innovationlabs.api")
@EntityScan(basePackages = "com.innovationlabs.api")
@ComponentScan(basePackages = "com.innovationlabs.api")
public class AssetManager {
    public static void main(String[] args) {
        SpringApplication.run(AssetManager.class, args);
    }
}
