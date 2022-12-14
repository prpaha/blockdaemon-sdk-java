package ru.prpaha.blockdaemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jnet.common.backend.service.configuration.YamlPropertySourceFactory;
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
    public Gson gson() {
        return new GsonBuilder().setDateFormat(BlockdaemonConstants.DATE_TIME_FORMAT).create();
    }

    @Bean
    public AccountsRepository accountRepository(AccountsApi accountsApi, Gson gson) {
        return new AccountsRepository(accountsApi, gson);
    }

    @Bean
    public TransactionsApi transactionsApi() {
        return new TransactionsApi(createApiClient());
    }

    @Bean
    public TransactionsRepository transactionsRepository(TransactionsApi transactionsApi, Gson gson) {
        return new TransactionsRepository(transactionsApi, gson);
    }

    @Bean
    public SyncApi syncApi() {
        return new SyncApi(createApiClient());
    }

    @Bean
    public PlatformsRepository platformsRepository(SyncApi syncApi, Gson gson) {
        return new PlatformsRepository(syncApi, gson);
    }

    private ApiClient createApiClient() {
        var apiClient = new ApiClient();
        ((HttpBearerAuth)apiClient.getAuthentication("bearerAuth")).setBearerToken(blockDaemonApiKey);
        apiClient.setDateFormat(new SimpleDateFormat(BlockdaemonConstants.DATE_TIME_FORMAT));
        apiClient.setDebugging(debugging);
        return apiClient;
    }

}
