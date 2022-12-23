package ru.prpaha.blockdaemon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BlockdaemonPlatform {

    BITCOIN("bitcoin"),
    ETHEREUM("ethereum"),
    ;

    private final String value;

}
