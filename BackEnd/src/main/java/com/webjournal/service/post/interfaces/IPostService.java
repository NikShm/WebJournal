package com.webjournal.service.post.interfaces;

import com.webjournal.dto.PostDTO;
import com.webjournal.entity.Post;

import java.util.List;

public interface IPostService {
    List<Post> getInterestingPosts();
}
