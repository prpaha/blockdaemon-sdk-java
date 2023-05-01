package ru.prpaha.blockdaemon.repository;

import lombok.AllArgsConstructor;
import ru.prpaha.blockdaemon.api.BlockIdentifiersApi;
import ru.prpaha.blockdaemon.api.BlocksApi;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.Block;
import ru.prpaha.blockdaemon.model.BlockIdentifier;

@AllArgsConstructor
public class BlocksRepository {

    private final BlocksApi blocksApi;
    private final BlockIdentifiersApi blockIdentifiersApi;

    public Block getBlockByNumber(String platform, String network, int number)
            throws ApiException {
        return blocksApi.getBlock(platform, network, String.valueOf(number));
    }

    public BlockIdentifier getBlockInfoByNumber(String platform, String network, int number)
            throws ApiException {
        return blockIdentifiersApi.getBlockIdentifier(platform, network, String.valueOf(number));
    }

}
