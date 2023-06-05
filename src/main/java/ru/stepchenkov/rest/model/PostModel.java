package ru.stepchenkov.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepchenkov.rest.entity.Post;

@Getter
@Setter
@NoArgsConstructor
public class PostModel {
    private String postName;

    public static PostModel toModel(Post post) {
        PostModel model = new PostModel();
        model.setPostName(post.getPostName());
        return model;
    }
}
