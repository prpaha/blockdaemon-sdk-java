package ru.prpaha.blockdaemon.repository;

import ru.prpaha.blockdaemon.api.SyncApi;
import ru.prpaha.blockdaemon.invoker.ApiException;

public class PlatformsRepository {

    private final SyncApi syncApi;

    public PlatformsRepository(SyncApi syncApi) {
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
