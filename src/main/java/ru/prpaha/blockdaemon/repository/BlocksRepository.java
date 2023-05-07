package ru.prpaha.blockdaemon.repository;

import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import ru.prpaha.blockdaemon.api.BlockIdentifiersApi;
import ru.prpaha.blockdaemon.api.BlocksApi;
import ru.prpaha.blockdaemon.invoker.ApiException;
import ru.prpaha.blockdaemon.model.Block;
import ru.prpaha.blockdaemon.model.BlockIdentifier;

@AllArgsConstructor
public class BlocksRepository {

    private final BlocksApi blocksApi;
    private final BlockIdentifiersApi blockIdentifiersApi;

    public Block getBlockByNumber(String platform, String network, int blockNumber)
            throws ApiException {
        var block = blocksApi.getBlock(platform, network, String.valueOf(blockNumber));
        if (block != null && !CollectionUtils.isEmpty(block.getTxs())) {
            block.getTxs().parallelStream().forEach(tx -> tx.setBlockNumber(blockNumber));
        }
        return block;
    }

    public BlockIdentifier getBlockInfoByNumber(String platform, String network, int number)
            throws ApiException {
        return blockIdentifiersApi.getBlockIdentifier(platform, network, String.valueOf(number));
    }

}
