package ru.prpaha.blockdaemon.repository;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import ru.prpaha.blockdaemon.invoker.ApiException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Proskurin Pavel (prpaha@rambler.ru)
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractApiRepository {

    private static final String FIELD_ERROR = "error";

    private final Gson gson;

    @Value("${simplex.debugging:false}")
    private boolean debugging;

    protected String call(Call call) throws ApiException {
        StringBuilder logBuilder = null;
        if (!debugging) {
            logBuilder = new StringBuilder("Call Blockdaemon API.");
            logBuilder.append("\nMethod: ").append(call.request().url());
        }
        try {
            String result = getStringBody(call.execute());
            if (!debugging) {
                logBuilder.append("\nResponse: ").append(result);
            }
            return result;
        } catch (IOException e) {
            log.error("Error on call Blockdaemon API", e);
            throw new ApiException(e);
        } finally {
            if (!debugging) {
                log.info(logBuilder.toString());
            }
        }
    }

    private String getStringBody(Response response) throws ApiException {
        String respBody = null;
        try {
            if (response.isSuccessful() && response.body() != null) {
                respBody = response.body().string();
                checkError(respBody, response);
            } else if (response.body() != null) {
                try {
                    respBody = response.body().string();
                } catch (IOException e) {
                    throw new ApiException(response.message(), e, response.code(), response.headers().toMultimap());
                }
            } else {
                throw new ApiException(response.message(), response.code(), response.headers().toMultimap(), respBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException(e);
        }
        return respBody;
    }

    private void checkError(String respBody, Response response) throws ApiException {
        Map dataMap = gson.fromJson(respBody, HashMap.class);
        if (dataMap.containsKey(FIELD_ERROR)) {
            throw new ApiException(dataMap.get(FIELD_ERROR).toString(), response.code(),
                    response.headers().toMultimap(), respBody);
        }
    }

    protected <I> I parseObject(String body, Class<I> clazz) {
        return gson.fromJson(body, clazz);
    }

}
