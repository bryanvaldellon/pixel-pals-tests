package steps;

import dto.MainDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SystemUtils;

@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentStep.class);
    private MainDto mainDto;

    public void setEnvironment() {
        this.mainDto.setTestEnv(SystemUtils.getSystemProperty("test.env"));
    }

    public void setEnvironment(String environment) {
        mainDto.setTestEnv(environment);
    }

    public void setAppEnvironment(String env) {
        this.mainDto.setApp(env);
    }
}
