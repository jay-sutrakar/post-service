package io.vertx.microservice;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostCommand {
    String title;
    String content;
}
