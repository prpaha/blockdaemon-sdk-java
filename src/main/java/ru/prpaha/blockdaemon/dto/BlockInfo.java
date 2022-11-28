package ru.prpaha.blockdaemon.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BlockInfo {

    String id;
    long number;

}
