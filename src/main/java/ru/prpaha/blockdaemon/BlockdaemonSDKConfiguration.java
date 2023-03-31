package ru.prpaha.blockdaemon;

import com.jnet.common.backend.service.configuration.YamlPropertySourceFactory;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.prpaha.blockdaemon.api.AccountsApi;
import ru.prpaha.blockdaemon.api.SyncApi;
import ru.prpaha.blockdaemon.api.TransactionsApi;
import ru.prpaha.blockdaemon.invoker.ApiClient;
import ru.prpaha.blockdaemon.invoker.auth.HttpBearerAuth;
import ru.prpaha.blockdaemon.repository.AccountsRepository;
import ru.prpaha.blockdaemon.repository.PlatformsRepository;
import ru.prpaha.blockdaemon.repository.TransactionsRepository;

import java.text.SimpleDateFormat;

/**
 * @author Proskurin Pavel (prpaha@rambler.ru)
 */
@Configuration
@PropertySource(value = "classpath:blockdaemon-sdk.yml", factory = YamlPropertySourceFactory.class)
@ComponentScan("ru.prpaha.blockdaemon")
public class BlockdaemonSDKConfiguration {

    @Value("${blockdaemon.apiKey}")
    private String blockDaemonApiKey;

    @Value("${blockdaemon.debugging:false}")
    private boolean debugging;

    @Bean
    public AccountsApi accountApi() {
        return new AccountsApi(createApiClient());
    }

    @Bean
    public AccountsRepository accountRepository(AccountsApi accountsApi) {
        return new AccountsRepository(accountsApi);
    }

    @Bean
    public TransactionsApi transactionsApi() {
        return new TransactionsApi(createApiClient());
    }

    @Bean
    public TransactionsRepository transactionsRepository(TransactionsApi transactionsApi) {
        return new TransactionsRepository(transactionsApi);
    }

    @Bean
    public SyncApi syncApi() {
        return new SyncApi(createApiClient());
    }

    @Bean
    public PlatformsRepository platformsRepository(SyncApi syncApi) {
        return new PlatformsRepository(syncApi);
    }

    private ApiClient createApiClient() {
        var apiClient = new ApiClient();
        ((HttpBearerAuth)apiClient.getAuthentication("bearerAuth")).setBearerToken(blockDaemonApiKey);
        apiClient.setDateFormat(new SimpleDateFormat(BlockdaemonConstants.DATE_TIME_FORMAT));
        apiClient.setDebugging(debugging);
        apiClient.getObjectMapper().registerModule(new JsonNullableModule());
        return apiClient;
    }

}
