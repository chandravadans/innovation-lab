import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by cv on 4/5/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.innovationlabs.api")
public class AssetManager {
    public static void main(String[] args) {
        SpringApplication.run(AssetManager.class, args);
    }
}
