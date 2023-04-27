package ru.prpaha.blockdaemon.repository;

import ru.prpaha.blockdaemon.api.BlocksApi;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.Block;

public class BlocksRepository {

    private final BlocksApi blocksApi;

    public BlocksRepository(BlocksApi blocksApi) {
        this.blocksApi = blocksApi;
    }

    public Block getBlockByNumber(String platform, String network, int number)
            throws ApiException {
        return blocksApi.getBlock(platform, network, String.valueOf(number));
    }

}
