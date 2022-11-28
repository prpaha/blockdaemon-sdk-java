package ru.prpaha.blockdaemon.repository;

import com.google.gson.Gson;
import ru.prpaha.blockdaemon.api.SyncApi;
import ru.prpaha.blockdaemon.invoker.ApiException;

public class PlatformsRepository extends AbstractApiRepository {

    private final SyncApi syncApi;

    public PlatformsRepository(SyncApi syncApi, Gson gson) {
        super(gson);
        this.syncApi = syncApi;
    }

    public String getCurrentBlockId(String platform, String network)
            throws ApiException {
        return syncApi.currentBlockID(platform, network);
    }

    public long getCurrentBlockNumber(String platform, String network)
            throws ApiException {
        return syncApi.currentBlockNumber(platform, network);
    }

}
