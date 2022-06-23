package io.vertx.microservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    String id;
    String title;
    String content;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    LocalDateTime createdAt;

    public static Post of(String title, String content) {
        return of(null, title, content, null);
    }

    public static Post of(String id, String title, String content, LocalDateTime createdAt) {
        Post data = new Post();
        data.setId(id);
        data.setTitle(title);
        data.setContent(content);
        data.setCreatedAt(createdAt);
        return data;
    }
}
