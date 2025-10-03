package net.xzh.pulsar.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息封装
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageDto {
    private Long id;
    private String content;
}
